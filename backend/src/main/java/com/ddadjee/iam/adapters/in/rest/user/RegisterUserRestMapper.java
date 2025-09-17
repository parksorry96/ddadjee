package com.ddadjee.iam.adapters.in.rest.user;

import com.ddadjee.iam.application.port.in.RegisterUserCommand;
import com.ddadjee.iam.domain.User;

/**
 * REST ↔ 애플리케이션 계층 매퍼.
 */
public final class RegisterUserRestMapper {

    private RegisterUserRestMapper() {}

    public static RegisterUserCommand toCommand(RegisterUserRequest request) {
        return new RegisterUserCommand(
                request.email(),
                request.username(),
                request.password()
        );
    }

    public static RegisterUserResponse toResponse(User user) {
        return new RegisterUserResponse(
                user.getId(),
                user.getEmail().getValue(),
                user.getUsername().getValue(),
                user.getCreatedAt()
        );
    }
}
