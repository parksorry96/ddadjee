package com.ddadjee.iam.application.service;

import com.ddadjee.common.error.BusinessException;
import com.ddadjee.common.error.ErrorCode;
import com.ddadjee.iam.application.port.in.LoginUserCommand;
import com.ddadjee.iam.application.port.in.LoginUserResult;
import com.ddadjee.iam.application.port.in.LoginUserResult.AuthTokens;
import com.ddadjee.iam.application.port.in.LoginUserUseCase;
import com.ddadjee.iam.application.port.out.LoadUserPort;
import com.ddadjee.iam.application.port.out.PasswordHasherPort;
import com.ddadjee.iam.application.port.out.TokenIssuerPort;
import com.ddadjee.iam.domain.Email;
import com.ddadjee.iam.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 로그인 애플리케이션 서비스.
 */
@Profile("oidc")
@Component
@Transactional(readOnly = true)
public record UserLoginService(
        LoadUserPort loadUserPort,
        PasswordHasherPort passwordHasherPort,
        TokenIssuerPort tokenIssuerPort
) implements LoginUserUseCase {

    private static final String INVALID_CREDENTIALS_MESSAGE = "이메일 또는 비밀번호가 올바르지 않습니다.";

    @Override
    public LoginUserResult login(LoginUserCommand command) {
        Email email = new Email(command.email());
        User user = loadUserPort.findByEmail(email)
                .orElseThrow(UserLoginService::invalidCredentials);

        if (!passwordHasherPort.matches(command.rawPassword(), user.getHashedPassword())) {
            throw invalidCredentials();
        }

        AuthTokens tokens = tokenIssuerPort.issueTokens(user);
        return new LoginUserResult(
                user.getId(),
                user.getEmail().getValue(),
                user.getUsername().getValue(),
                tokens
        );
    }

    private static BusinessException invalidCredentials() {
        return new BusinessException(ErrorCode.UNAUTHORIZED, INVALID_CREDENTIALS_MESSAGE);
    }
}
