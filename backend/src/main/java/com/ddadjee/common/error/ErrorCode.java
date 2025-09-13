package com.ddadjee.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 공통 오류 코드 열거형.
 * <p>
 * HTTP 상태와 기본 메시지를 함께 보관하여, 컨트롤러에서 일관된 오류 응답을 생성할 수 있게 합니다.
 */
@Getter
public enum ErrorCode {
    BAD_REQUEST("E400", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED("E401", HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN("E403", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND("E404", HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다."),
    CONFLICT("E409", HttpStatus.CONFLICT, "충돌이 발생했습니다."),
    VALIDATION_ERROR("E422", HttpStatus.UNPROCESSABLE_ENTITY, "검증 오류입니다."),
    INTERNAL_ERROR("E500", HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    /** 외부 노출용 오류 코드 문자열 (예: E422) */
    private final String code;
    /** 매핑되는 HTTP 상태 코드 */
    private final HttpStatus status;
    /** 기본 메시지(별도 메시지 미지정 시 사용) */
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus status, String defaultMessage) {
        this.code = code;
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
}
