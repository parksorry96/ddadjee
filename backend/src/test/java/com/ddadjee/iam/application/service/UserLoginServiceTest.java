package com.ddadjee.iam.application.service;

import com.ddadjee.common.error.BusinessException;
import com.ddadjee.common.error.ErrorCode;
import com.ddadjee.iam.application.port.in.LoginUserCommand;
import com.ddadjee.iam.application.port.in.LoginUserResult;
import com.ddadjee.iam.application.port.out.LoadUserPort;
import com.ddadjee.iam.application.port.out.PasswordHasherPort;
import com.ddadjee.iam.application.port.out.TokenIssuerPort;
import com.ddadjee.iam.domain.Email;
import com.ddadjee.iam.domain.User;
import com.ddadjee.iam.domain.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserLoginServiceTest {

    @Mock
    private LoadUserPort loadUserPort;
    @Mock
    private PasswordHasherPort passwordHasherPort;
    @Mock
    private TokenIssuerPort tokenIssuerPort;

    @InjectMocks
    private UserLoginService userLoginService;

    private User existingUser;

    @BeforeEach
    void setUp() {
        existingUser = User.rehydrate(
                UUID.randomUUID(),
                new Email("login-user@example.com"),
                new Username("login-user"),
                "$bcrypt$hashed$",
                ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant()
        );
    }

    @Test
    @DisplayName("정상 로그인 시 토큰 페어를 반환한다")
    void login_successful() {
        LoginUserCommand command = new LoginUserCommand(existingUser.getEmail().getValue(), "plainPassword1!");
        LoginUserResult.AuthTokens issuedTokens = new LoginUserResult.AuthTokens(
                "access-token",
                Instant.parse("2025-09-24T10:00:00Z"),
                "refresh-token",
                Instant.parse("2025-09-25T10:00:00Z"),
                "Bearer",
                Set.of("openid", "profile")
        );

        when(loadUserPort.findByEmail(new Email(command.email()))).thenReturn(Optional.of(existingUser));
        when(passwordHasherPort.matches(command.rawPassword(), existingUser.getHashedPassword())).thenReturn(true);
        when(tokenIssuerPort.issueTokens(existingUser)).thenReturn(issuedTokens);

        LoginUserResult result = userLoginService.login(command);

        assertThat(result.userId()).isEqualTo(existingUser.getId());
        assertThat(result.email()).isEqualTo(existingUser.getEmail().getValue());
        assertThat(result.username()).isEqualTo(existingUser.getUsername().getValue());
        assertThat(result.tokens()).isEqualTo(issuedTokens);

        ArgumentCaptor<String> rawCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> hashedCaptor = ArgumentCaptor.forClass(String.class);
        verify(passwordHasherPort).matches(rawCaptor.capture(), hashedCaptor.capture());
        assertThat(rawCaptor.getValue()).isEqualTo(command.rawPassword());
        assertThat(hashedCaptor.getValue()).isEqualTo(existingUser.getHashedPassword());

        verify(tokenIssuerPort).issueTokens(existingUser);
    }

    @Test
    @DisplayName("사용자가 존재하지 않으면 UNAUTHORIZED 비즈니스 예외를 던진다")
    void login_userNotFound() {
        LoginUserCommand command = new LoginUserCommand("missing@example.com", "plainPassword1!");
        when(loadUserPort.findByEmail(new Email(command.email()))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userLoginService.login(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이메일 또는 비밀번호가 올바르지 않습니다.")
                .satisfies(ex -> assertThat(((BusinessException) ex).getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED));
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 UNAUTHORIZED 비즈니스 예외를 던진다")
    void login_passwordMismatch() {
        LoginUserCommand command = new LoginUserCommand(existingUser.getEmail().getValue(), "wrongPassword1!");

        when(loadUserPort.findByEmail(new Email(command.email()))).thenReturn(Optional.of(existingUser));
        when(passwordHasherPort.matches(command.rawPassword(), existingUser.getHashedPassword())).thenReturn(false);

        assertThatThrownBy(() -> userLoginService.login(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이메일 또는 비밀번호가 올바르지 않습니다.")
                .satisfies(ex -> assertThat(((BusinessException) ex).getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED));
    }
}
