package com.ddadjee.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

/**
 * Jackson ObjectMapper 공통 설정.
 * <p>
 * - Java Time 직렬화 모듈 등록
 * - 날짜를 타임스탬프가 아닌 ISO-8601 문자열로 출력
 * - 알 수 없는 속성 무시(하위호환성 향상)
 * - null 필드는 직렬화에서 제외
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Spring Boot 기본 ObjectMapper에 추가 커스터마이징을 적용합니다.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return (Jackson2ObjectMapperBuilder builder) -> builder
                .modules(new JavaTimeModule())
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
