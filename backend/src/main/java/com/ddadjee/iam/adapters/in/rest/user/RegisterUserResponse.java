package com.ddadjee.iam.adapters.in.rest.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

/**
 * 회원가입 성공 응답 DTO.
 */
public record RegisterUserResponse(
        @Schema(description = "신규 사용자 식별자")
        UUID id,
        @Schema(description = "가입 이메일")
        String email,
        @Schema(description = "사용자명")
        String username,
        @Schema(description = "가입 시각(UTC)")
        Instant createdAt
) {}
