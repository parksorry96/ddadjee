package com.ddadjee.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Bean Validation 공통 설정.
 * <p>
 * - 메시지 소스를 연결하여 커스텀 메시지 키 사용
 * - @Validated 메서드 레벨 검증 활성화
 */
@Configuration
public class ValidationConfiguration {

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(LocalValidatorFactoryBean validator) {
        MethodValidationPostProcessor p = new MethodValidationPostProcessor();
        p.setValidator(validator);
        return p;
    }
}

