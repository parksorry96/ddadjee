package com.ddadjee.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 요청 단위 Correlation ID(X-Request-Id) 필터 등록.
 * <p>
 * - 요청 헤더에 X-Request-Id가 없으면 생성하여 응답 헤더에도 추가합니다.
 * - 로그 MDC에 requestId를 넣어 호출 추적을 돕습니다.
 */
@Configuration
public class RequestIdFilterConfiguration {

    /**
     * 필터 정의: 요청 단위로 MDC에 requestId 설정/해제.
     */
    static class RequestIdFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String id = request.getHeader("X-Request-Id");
            if (id == null || id.isBlank()) {
                id = UUID.randomUUID().toString();
                response.setHeader("X-Request-Id", id);
            }
            MDC.put("requestId", id);
            try {
                filterChain.doFilter(request, response);
            } finally {
                MDC.remove("requestId");
            }
        }
    }

    /**
     * 필터 등록 빈.
     */
    @Bean
    public FilterRegistrationBean<RequestIdFilter> requestIdFilterRegistration() {
        FilterRegistrationBean<RequestIdFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new RequestIdFilter());
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE);
        reg.addUrlPatterns("/*");
        return reg;
    }
}

