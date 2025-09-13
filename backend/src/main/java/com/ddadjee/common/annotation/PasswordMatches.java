package com.ddadjee.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 두 필드의 값이 일치하는지 검증하는 타입 레벨 애너테이션.
 * <p>
 * 예) 비밀번호/비밀번호 확인 필드 동일성 검사.
 */
@Documented
@Constraint(validatedBy = com.ddadjee.common.validation.PasswordMatchesValidator.class)
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface PasswordMatches {

    /** 비교할 첫 번째 필드명 */
    String first();

    /** 비교할 두 번째 필드명 */
    String second();

    String message() default "두 필드 값이 일치하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

