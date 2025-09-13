package com.ddadjee.common.annotation;

import java.lang.annotation.*;

/**
 * 민감 정보 마스킹 표시 애너테이션.
 * <p>
 * - 파라미터/필드에 부착하여 로깅 시 값을 마스킹하도록 힌트를 제공합니다.
 * - 마스킹 동작은 별도의 AOP에서 구현합니다.
 */
@Documented
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {
    /**
     * 마스킹 대체 문자열.
     */
    String maskWith() default "****";

    /**
     * 끝자리 일부 노출(예: 카드번호 마지막 4자리).
     * 0이면 전부 마스킹.
     */
    int exposeTail() default 0;
}
