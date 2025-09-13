package com.ddadjee.common.annotation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * 조회 전용 유스케이스 메타 어노테이션
 * <p>
 * - 읽기 전용 트랜젝션으로 마킹
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Transactional(readOnly = true)
public @interface ReadOnlyUseCase {
}
