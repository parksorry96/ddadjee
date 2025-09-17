package com.ddadjee.iam.configuration;

import com.ddadjee.iam.application.port.out.DomainEventPublisherPort;
import com.ddadjee.iam.application.port.out.LoadUserPort;
import com.ddadjee.iam.application.port.out.PasswordHasherPort;
import com.ddadjee.iam.application.port.out.SaveUserPort;
import com.ddadjee.iam.application.service.UserRegistrationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * IAM 애플리케이션 서비스 빈 구성.
 * - 애플리케이션 계층(UseCase 레코드)은 프레임워크에 독립적인 POJO 유지.
 * - 스프링 빈 등록은 구성 클래스를 통해 수행.
 */
@Configuration
public class UserApplicationConfig {

    @Bean
    public UserRegistrationService userRegistrationService(
            LoadUserPort loadUser,
            SaveUserPort saveUser,
            PasswordHasherPort passwordHasher,
            DomainEventPublisherPort domainEventPublisher,
            Clock clock
    ) {
        return new UserRegistrationService(
                loadUser,
                saveUser,
                passwordHasher,
                domainEventPublisher,
                clock
        );
    }
}
