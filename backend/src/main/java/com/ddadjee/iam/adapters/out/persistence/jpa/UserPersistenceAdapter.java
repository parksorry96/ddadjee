package com.ddadjee.iam.adapters.out.persistence.jpa;

import com.ddadjee.iam.application.port.out.LoadUserPort;
import com.ddadjee.iam.application.port.out.SaveUserPort;
import com.ddadjee.iam.domain.Email;
import com.ddadjee.iam.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * SaveUserPort, LoadUserPort 구현
 * Spring Data Jpa Repository 를 주입받아 Domain <->JPA 간 변환을담당
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(Email email) {
        return userJpaRepository.findByEmail(email.getValue())
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public User save(User user) {
        UserJpaEntity saved = userJpaRepository.save(UserJpaEntity.fromDomain(user));
        return saved.toDomain();
    }
}
