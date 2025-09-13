package com.ddadjee.common.error;

import com.ddadjee.common.web.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 전역 예외 처리기.
 * <p>
 * - 비즈니스/검증/시스템 오류를 잡아 통일된 응답 스키마({@link ApiResponse})로 변환합니다.
 * - 오류 상세는 {@link ErrorResponse}로 전달됩니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 비즈니스 예외 처리 */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBusiness(
            BusinessException exception,
            HttpServletRequest request
    ) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorBody = new ErrorResponse(
                errorCode.getCode(),
                exception.getMessage(),
                request.getRequestURI(),
                Instant.now(),
                null
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode, exception.getMessage(), errorBody));
    }

    /** 바디 바인딩 검증 실패(@Valid) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<ErrorResponse.Violation> violations = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toViolation)
                .collect(Collectors.toList());

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        ErrorResponse errorBody = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDefaultMessage(),
                request.getRequestURI(),
                Instant.now(),
                violations
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode, errorCode.getDefaultMessage(), errorBody));
    }

    /** 파라미터 검증 실패(메서드 파라미터/경로 변수) */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        List<ErrorResponse.Violation> violations = exception.getConstraintViolations()
                .stream()
                .map(v -> new ErrorResponse.Violation(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());

        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        ErrorResponse errorBody = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDefaultMessage(),
                request.getRequestURI(),
                Instant.now(),
                violations
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode, errorCode.getDefaultMessage(), errorBody));
    }

    /** 파라미터 타입 불일치(예: id=abc → Long) */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        String message = "요청 파라미터 타입이 올바르지 않습니다.";
        ErrorResponse errorBody = new ErrorResponse(
                errorCode.getCode(),
                message,
                request.getRequestURI(),
                Instant.now(),
                null
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode, message, errorBody));
    }

    /** 마지막 안전망: 예기치 못한 오류 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnknown(
            Exception exception,
            HttpServletRequest request
    ) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        ErrorResponse errorBody = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDefaultMessage(),
                request.getRequestURI(),
                Instant.now(),
                null
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode, exception.getMessage(), errorBody));
    }

    /** FieldError → Violation 매핑 */
    private ErrorResponse.Violation toViolation(FieldError fieldError) {
        return new ErrorResponse.Violation(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
