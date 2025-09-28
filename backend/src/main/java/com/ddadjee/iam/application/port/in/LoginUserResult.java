package com.ddadjee.iam.application.port.in;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * 로그인 결과 DTO.
 * <p>
 * 토큰 페어와 사용자 기본 정보를 포함합니다.
 */
public record LoginUserResult(
        UUID userId,
        String email,
        String username,
        AuthTokens tokens
) {

    public LoginUserResult {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(tokens, "tokens must not be null");
    }

    /**
     * 발급된 인증 토큰 세트.
     * access/refresh 토큰과 만료 시각, 토큰 타입 및 scope 정보를 포함합니다.
     */
    public record AuthTokens(
            String accessToken,
            Instant accessTokenExpiresAt,
            String refreshToken,
            Instant refreshTokenExpiresAt,
            String tokenType,
            Set<String> scopes
    ) {
        public AuthTokens {
            Objects.requireNonNull(accessToken, "accessToken must not be null");
            Objects.requireNonNull(accessTokenExpiresAt, "accessTokenExpiresAt must not be null");
            Objects.requireNonNull(refreshToken, "refreshToken must not be null");
            Objects.requireNonNull(refreshTokenExpiresAt, "refreshTokenExpiresAt must not be null");
            Objects.requireNonNull(tokenType, "tokenType must not be null");
            scopes = scopes == null ? Set.of() : Set.copyOf(scopes);
        }
    }
}
