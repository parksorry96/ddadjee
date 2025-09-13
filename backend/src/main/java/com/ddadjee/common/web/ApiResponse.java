package com.ddadjee.common.web;

/**
 * 공통 API 응답 래퍼.
 * <p>
 * - 성공/실패 여부와 코드·메시지를 통일된 스키마로 전달합니다.
 * - 성공 시 {@code success=true, code="OK"}를 권장합니다.
 * - 실패 시 {@link #error(String, String, Object)} 팩토리 메서드를 사용합니다.
 *
 * @param <T> 응답 데이터 타입
 */
public record ApiResponse<T>(
        boolean success,   /** 성공 여부 */
        String code,       /** 응답/오류 코드(성공 시 "OK") */
        String message,    /** 설명 메시지(선택) */
        T data             /** 응답 데이터(또는 오류 상세) */
) {

    /**
     * 성공 응답(메시지 없이).
     * @param data 응답 데이터
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "OK", null, data);
    }

    /**
     * 성공 응답(메시지 포함).
     * @param data 응답 데이터
     * @param message 메시지
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, "OK", message, data);
    }

    /**
     * 오류 응답. ErrorCode 사용 없이 코드/메시지를 직접 지정할 때 사용합니다.
     * @param code 오류 코드 문자열(예: "E422")
     * @param message 사용자에게 노출할 메시지
     * @param data 오류 상세 페이로드(예: ErrorResponse)
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }

    /**
     * 오류 응답(공통 ErrorCode 사용).
     * @param errorCode 공통 오류 코드
     * @param message null 이면 기본 메시지를 사용
     * @param data 오류 상세 페이로드(예: ErrorResponse)
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(
            com.ddadjee.common.error.ErrorCode errorCode,
            String message,
            T data
    ) {
        String msg = (message != null && !message.isBlank()) ? message : errorCode.getDefaultMessage();
        return error(errorCode.getCode(), msg, data);
    }
}
