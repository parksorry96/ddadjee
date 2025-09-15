package com.ddadjee.common.error;

import com.ddadjee.common.web.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.Instant;

/**
 * Spring Security 예외를 JSON 응답으로 쓰는 헬퍼.
 */
public final class ApiErrorJsonWriters {

    private ApiErrorJsonWriters() {}

    /**
     * 401 Unauthorized 응답 작성 엔트리포인트.
     */
    public static AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper om) {
        return (request, response, authException) -> write(response, request, ErrorCode.UNAUTHORIZED, authException.getMessage(), om);
    }

    /**
     * 403 Forbidden 응답 작성 핸들러.
     */
    public static AccessDeniedHandler accessDeniedHandler(ObjectMapper om) {
        return (request, response, accessDeniedException) -> write(response, request, ErrorCode.FORBIDDEN, accessDeniedException.getMessage(), om);
    }

    private static void write(HttpServletResponse resp, HttpServletRequest req, ErrorCode ec, String message, ObjectMapper om) throws IOException {
        resp.setStatus(ec.getStatus().value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse body = new ErrorResponse(
                ec.getCode(),
                (message != null && !message.isBlank()) ? message : ec.getDefaultMessage(),
                req.getRequestURI(),
                Instant.now(),
                null
        );
        var api = ApiResponse.error(ec, body.message(), body);
        om.writeValue(resp.getOutputStream(), api);
    }
}

