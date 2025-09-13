package com.ddadjee.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 비밀번호 강도 검증 애너테이션.
 * <p>
 * 길이/대소문자/숫자/특수문자 포함 여부를 설정할 수 있습니다.
 */
@Documented
@Constraint(validatedBy = com.ddadjee.common.validation.ValidPasswordValidator.class)
@Target({ FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {

    /** 최소 길이 (기본 8자) */
    int min() default 8;

    /** 최대 길이 (기본 128자) */
    int max() default 128;

    /** 대문자 최소 1자 요구 여부 */
    boolean requireUppercase() default true;

    /** 소문자 최소 1자 요구 여부 */
    boolean requireLowercase() default true;

    /** 숫자 최소 1자 요구 여부 */
    boolean requireDigit() default true;

    /** 특수문자 최소 1자 요구 여부 */
    boolean requireSpecial() default true;

    /** 검증 실패 시 기본 메시지 키 */
    String message() default "{validation.password.rule}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
