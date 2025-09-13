package com.ddadjee.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 간단한 분당/초당 요청 제한을 위한 애너테이션.
 * <p>
 * Redis 카운터 기반 고정 윈도우 알고리즘을 사용합니다.
 */
@Documented
@Target({ METHOD })
@Retention(RUNTIME)
public @interface RateLimiting {

    /** SpEL 키(예: "#userId", "#request.id"). 비워두면 메서드 시그니처 기준. */
    String key() default "";

    /** 허용 요청 수(윈도우 내). */
    int permits() default 30;

    /** 윈도우(초). 예: 60 → 1분, 1 → 1초. */
    int windowSeconds() default 60;

    /** 한도 초과 메시지(미지정 시 기본 메시지 사용). */
    String message() default "요청 한도를 초과하였습니다.";
}

