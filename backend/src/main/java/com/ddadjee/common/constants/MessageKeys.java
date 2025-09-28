package com.ddadjee.common.constants;

/**
 * i18n/검증/오류 메시지 키 상수.
 * <p>
 * 컨트롤러/서비스/검증 애너테이션에서 메시지 키를 하드코딩하지 않고
 * 정적 임포트로 사용하기 위한 키 네임을 모아둡니다.
 */
public final class MessageKeys {

    private MessageKeys() {}

    /** 일반 */
    public static final String OK = "ok";
    public static final String ERROR = "error";

    /** 검증(Validation) 공통 */
    public static final String VALIDATION_INVALID_REQUEST = "validation.invalid.request";
    public static final String VALIDATION_NOT_BLANK = "validation.notBlank";
    public static final String VALIDATION_NOT_NULL = "validation.notNull";
    public static final String VALIDATION_EMAIL = "validation.email";
    public static final String VALIDATION_PHONE = "validation.phone";
    public static final String VALIDATION_URL = "validation.url";
    public static final String VALIDATION_USERNAME = "validation.username";
    public static final String VALIDATION_UUID = "validation.uuid";

    /** 커스텀 검증 */
    public static final String VALIDATION_PASSWORD_RULE = "validation.password.rule";
    public static final String VALIDATION_PASSWORD_MISMATCH = "validation.password.mismatch";
    public static final String VALIDATION_EMAIL_UNIQUE = "validation.email.unique";

    /** 도메인별 예시 (필요 시 확장) */
    public static final String IAM_USER_ALREADY_EXISTS = "iam.user.alreadyExists";
    public static final String IAM_USER_NOT_FOUND = "iam.user.notFound";
    public static final String IAM_USER_INVALID_CREDENTIALS = "iam.user.invalidCredentials";
    public static final String CATALOG_CERT_NOT_FOUND = "catalog.certification.notFound";
}
