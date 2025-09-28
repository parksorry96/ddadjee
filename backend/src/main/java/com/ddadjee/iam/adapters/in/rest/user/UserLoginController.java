package com.ddadjee.iam.adapters.in.rest.user;

import com.ddadjee.common.web.ApiResponse;
import com.ddadjee.iam.application.port.in.LoginUserUseCase;
import com.ddadjee.iam.application.port.in.LoginUserResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.ddadjee.iam.adapters.in.rest.user.LoginUserRestMapper.toCommand;
import static com.ddadjee.iam.adapters.in.rest.user.LoginUserRestMapper.toResponse;

/**
 * 로그인 REST 컨트롤러.
 */
@RestController
@RequestMapping(UserApiRoutes.USERS_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "IAM Users")
@Profile("oidc")
public class UserLoginController {

    private final LoginUserUseCase loginUserUseCase;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인")
    public ApiResponse<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request) {
        LoginUserResult result = loginUserUseCase.login(toCommand(request));
        return ApiResponse.ok(toResponse(result), "로그인이 완료되었습니다.");
    }
}
