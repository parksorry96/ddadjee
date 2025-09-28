package com.ddadjee.iam.adapters.in.rest.user;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * 로그인 REST 응답 DTO.
 */
public record LoginUserResponse(
        UUID userId,
        String email,
        String username,
        String accessToken,
        Instant accessTokenExpiresAt,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        String tokenType,
        List<String> scopes
) {
}
