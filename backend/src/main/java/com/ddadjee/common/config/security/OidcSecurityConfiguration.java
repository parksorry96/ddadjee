package com.ddadjee.common.config.security;

import com.ddadjee.common.error.ApiErrorJsonWriters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * OIDC 리소스 서버 보안 설정(프로필: oidc).
 * <p>
 * - Swagger/Actuator 등 공개 엔드포인트 허용
 * - 그 외 /api/** 는 JWT 인증 요구
 */
@Configuration
@EnableMethodSecurity
@Profile("oidc")
public class OidcSecurityConfiguration {

    @Bean
    public SecurityFilterChain oidcSecurityFilterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", 
                                "/swagger", 
                                "/swagger-ui/**", 
                                "/api-docs/**", 
                                "/v3/api-docs/**", 
                                "/actuator/**"
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth -> oauth.jwt())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(ApiErrorJsonWriters.authenticationEntryPoint(objectMapper))
                        .accessDeniedHandler(ApiErrorJsonWriters.accessDeniedHandler(objectMapper))
                );
        return http.build();
    }
}

