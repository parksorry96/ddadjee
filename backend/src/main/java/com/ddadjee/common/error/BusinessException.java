package com.ddadjee.common.error;

/**
 * 비즈니스/도메인 예외.
 * <p>
 * 도메인 규칙 위반 또는 애플리케이션 정책 위반을 표현합니다.
 * 컨트롤러 어드바이스에서 {@link ErrorCode}에 매핑된 HTTP 상태로
 * 일관된 오류 응답을 반환하도록 설계되어 있습니다.
 */
public class BusinessException extends RuntimeException {
    /** 어떤 오류인지 식별하는 공통 오류 코드 */
    private final ErrorCode errorCode;

    /**
     * 커스텀 메시지를 포함하는 생성자.
     * @param errorCode 공통 오류 코드
     * @param message 사용자에게 노출할 메시지(선택)
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 기본 메시지를 사용하는 생성자.
     * @param errorCode 공통 오류 코드
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    /** 오류 코드 반환 */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
