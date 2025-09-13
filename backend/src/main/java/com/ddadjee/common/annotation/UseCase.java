package com.ddadjee.common.annotation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * 어플리케이션 유스케이스 컴포넌트 메타 어노테이션
 * <p>
 * - 트랜젝션 경계를 기본 제공
 * - Controller, Repository, Domain영역에는 사용 X
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Transactional
public @interface UseCase {
}
