package com.ddadjee.common.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 현재 인증된 사용자 주입을 위한 메타 애너테이션.
 * <p>
 * Spring Security의 {@link AuthenticationPrincipal}을 감싸서 컨트롤러 메서드 파라미터에 사용합니다.
 * 예)
 * <pre>
 * public ApiResponse<?> me(@CurrentUser org.springframework.security.oauth2.jwt.Jwt jwt) { ... }
 * </pre>
 * 또는 커스텀 Principal 타입을 도입한 뒤에도 동일하게 사용할 수 있습니다.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {
}

