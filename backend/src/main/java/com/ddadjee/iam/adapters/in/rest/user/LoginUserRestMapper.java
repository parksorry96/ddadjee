package com.ddadjee.iam.adapters.in.rest.user;

import com.ddadjee.iam.application.port.in.LoginUserCommand;
import com.ddadjee.iam.application.port.in.LoginUserResult;

import java.util.List;

/**
 * 로그인 REST ↔ 애플리케이션 계층 매퍼.
 */
public final class LoginUserRestMapper {

    private LoginUserRestMapper() {
    }

    public static LoginUserCommand toCommand(LoginUserRequest request) {
        return new LoginUserCommand(
                request.email(),
                request.password()
        );
    }

    public static LoginUserResponse toResponse(LoginUserResult result) {
        return new LoginUserResponse(
                result.userId(),
                result.email(),
                result.username(),
                result.tokens().accessToken(),
                result.tokens().accessTokenExpiresAt(),
                result.tokens().refreshToken(),
                result.tokens().refreshTokenExpiresAt(),
                result.tokens().tokenType(),
                List.copyOf(result.tokens().scopes())
        );
    }
}
