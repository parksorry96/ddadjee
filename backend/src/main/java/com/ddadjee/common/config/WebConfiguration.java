package com.ddadjee.common.config;

import com.ddadjee.common.config.properties.CorsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 공통 설정.
 * <p>
 * - 전역 CORS 허용 정책(기본: 프런트 개발 서버 http://localhost:5173)
 * - 필요한 경우 추가 포맷터/컨버터도 여기에 등록합니다.
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class WebConfiguration implements WebMvcConfigurer {

    private final CorsProperties cors;

    public WebConfiguration(CorsProperties cors) {
        this.cors = cors;
    }

    /**
     * 전역 CORS 매핑: `/api/**` 경로에만 적용.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(cors.getAllowedOrigins().toArray(String[]::new))
                .allowedMethods(cors.getAllowedMethods().toArray(String[]::new))
                .allowedHeaders(cors.getAllowedHeaders().toArray(String[]::new))
                .allowCredentials(cors.isAllowCredentials())
                .maxAge(cors.getMaxAgeSec());
    }
}

