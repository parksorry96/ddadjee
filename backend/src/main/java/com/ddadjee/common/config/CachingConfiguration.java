package com.ddadjee.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Cache 활성화 설정.
 * <p>
 * - CacheManager는 Spring Boot 자동 구성에 따릅니다.
 * - application.yml의 spring.cache.* 설정으로 Redis를 캐시 저장소로 사용합니다.
 */
@Configuration
@EnableCaching
public class CachingConfiguration {
}

