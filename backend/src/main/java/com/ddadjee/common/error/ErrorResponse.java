package com.ddadjee.common.error;

import java.time.Instant;
import java.util.List;

/**
 * 오류 응답 페이로드.
 * <p>
 * 컨트롤러에서 실패 응답 시 {@code ApiResponse<ErrorResponse>} 형태로 반환하며,
 * 코드/메시지/요청경로/타임스탬프 및 필드 검증 오류 목록을 제공합니다.
 * <p>
 * 검증 오류가 없을 경우 {@code violations}는 {@code null} 또는 빈 리스트가 될 수 있습니다.
 */
public record ErrorResponse(
        String code,                 /** ErrorCode.code (예: E422) */
        String message,              /** 사용자용 메시지 */
        String path,                 /** 요청 경로 (예: /api/users) */
        Instant timestamp,           /** 오류 발생 시각(UTC) */
        List<Violation> violations   /** 필드별 검증 오류 목록 */
) {
    /** 단일 필드 검증 오류 항목 */
    public record Violation(String field, String reason) {}
}
