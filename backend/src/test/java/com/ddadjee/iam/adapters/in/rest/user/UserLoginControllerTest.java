package com.ddadjee.iam.adapters.in.rest.user;

import com.ddadjee.iam.application.port.in.LoginUserCommand;
import com.ddadjee.iam.application.port.in.LoginUserResult;
import com.ddadjee.iam.application.port.in.LoginUserResult.AuthTokens;
import com.ddadjee.iam.application.port.in.LoginUserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserLoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("oidc")
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginUserUseCase loginUserUseCase;

    @Test
    @DisplayName("로그인 요청이 오면 UseCase를 호출하고 토큰 응답을 반환한다")
    void login_success() throws Exception {
        LoginUserRequest request = new LoginUserRequest("login-user@example.com", "plainPassword1!");
        UUID userId = UUID.randomUUID();
        AuthTokens tokens = new AuthTokens(
                "access-token",
                Instant.parse("2025-09-24T10:00:00Z"),
                "refresh-token",
                Instant.parse("2025-09-25T10:00:00Z"),
                "Bearer",
                Set.of("openid", "profile")
        );
        LoginUserResult result = new LoginUserResult(
                userId,
                request.email(),
                "login-user",
                tokens
        );

        when(loginUserUseCase.login(any(LoginUserCommand.class))).thenReturn(result);

        mockMvc.perform(post(UserApiRoutes.USERS_BASE_PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value(userId.toString()))
                .andExpect(jsonPath("$.data.email").value(request.email()))
                .andExpect(jsonPath("$.data.username").value("login-user"))
                .andExpect(jsonPath("$.data.accessToken").value(tokens.accessToken()))
                .andExpect(jsonPath("$.data.refreshToken").value(tokens.refreshToken()))
                .andExpect(jsonPath("$.data.accessTokenExpiresAt").value(tokens.accessTokenExpiresAt().toString()))
                .andExpect(jsonPath("$.data.refreshTokenExpiresAt").value(tokens.refreshTokenExpiresAt().toString()))
                .andExpect(jsonPath("$.data.tokenType").value(tokens.tokenType()))
                .andExpect(jsonPath("$.data.scopes", containsInAnyOrder("openid", "profile")));

        ArgumentCaptor<LoginUserCommand> commandCaptor = ArgumentCaptor.forClass(LoginUserCommand.class);
        verify(loginUserUseCase).login(commandCaptor.capture());
        LoginUserCommand captured = commandCaptor.getValue();
        assertThat(captured.email()).isEqualTo(request.email());
        assertThat(captured.rawPassword()).isEqualTo(request.password());
    }
}
