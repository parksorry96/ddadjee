package com.ddadjee.iam.application.service;

import com.ddadjee.common.annotation.UseCase;
import com.ddadjee.common.annotation.UseCase;
import com.ddadjee.common.error.BusinessException;
import com.ddadjee.common.error.ErrorCode;
import com.ddadjee.iam.application.port.in.RegisterUserCommand;
import com.ddadjee.iam.application.port.in.RegisterUserUseCase;
import com.ddadjee.iam.application.port.out.DomainEventPublisherPort;
import com.ddadjee.iam.application.port.out.LoadUserPort;
import com.ddadjee.iam.application.port.out.PasswordHasherPort;
import com.ddadjee.iam.application.port.out.SaveUserPort;
import com.ddadjee.iam.domain.Email;
import com.ddadjee.iam.domain.User;
import com.ddadjee.iam.domain.Username;

import java.time.Clock;

/**
 * User 회원가입 Application Service
 *      -Port에만 의존하는 POJO 코드 (Spring/Infra 에 독립적)
 * @param loadUser
 * @param saveUser
 * @param passwordHasher
 * @param domainEventPublisher
 * @param clock
 */
public record UserRegistrationService (
        LoadUserPort loadUser,
        SaveUserPort saveUser,
        PasswordHasherPort passwordHasher,
        DomainEventPublisherPort domainEventPublisher,
        Clock clock
) implements RegisterUserUseCase {

    @Override
    public User register(RegisterUserCommand command) {
        Email email = new Email(command.email());
        Username username = new Username(command.username());

        loadUser.findByEmail(email).ifPresent(user->{
            throw new BusinessException(ErrorCode.CONFLICT, "이미 가입된 이메일입니다.");
        });

        String hashed = passwordHasher.hash(command.rawPassword());
        User user = User.registerNew(email,username,hashed,clock);
        User saved = saveUser.save(user);
        domainEventPublisher.publishAll(saved.pullDomainEvents());
        return saved;
    }
}
