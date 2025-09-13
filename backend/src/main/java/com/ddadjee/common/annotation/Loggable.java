package com.ddadjee.common.annotation;

import java.lang.annotation.*;

/**
 * 메서드/클래스 실행 로깅용 어노테이션
 * <p>
 * - AOP에서 START/END, 소요시간을 로깅합니다.
 * - args/result 플래그로 인자/결과 로깅을 제어합니다(민감정보 주의).
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    /**
     * 메서드 인자 로깅 여부(민감정보가 포함될 수 있으니 주의).
     */
    boolean args() default false;

    /**
     * 메서드 결과값 로깅 여부.
     */
    boolean result() default false;
}
