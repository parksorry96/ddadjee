package com.ddadjee.iam.application.port.in;

import com.ddadjee.iam.domain.User;

/**
 * 회원가입 UseCase Interface
 */
public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);
}
