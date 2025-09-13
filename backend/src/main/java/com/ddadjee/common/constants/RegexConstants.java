package com.ddadjee.common.constants;

import java.util.regex.Pattern;

/**
 * 정규식 상수 모음.
 * <p>
 * - 서비스 전반에서 자주 쓰이는 입력 형식 검증을 위한 정규식을 제공합니다.
 * - 정규식 문자열과 미리 컴파일된 {@link Pattern}을 모두 제공해 정적 임포트로 손쉽게 사용 가능합니다.
 */
public final class RegexConstants {

    private RegexConstants() {}

    /** 간단 이메일 패턴 (RFC 5322 대부분 커버, 실무용 간소화) */
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    /** 미리 컴파일된 이메일 패턴 */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL);

    /** E.164 국제 전화번호 (예: +821012345678, +12025550123) */
    public static final String PHONE_E164 = "^\\+?[1-9]\\d{1,14}$";
    public static final Pattern PHONE_E164_PATTERN = Pattern.compile(PHONE_E164);

    /** HTTP/HTTPS URL (간소화) */
    public static final String URL_HTTP = "^(https?)://[\\w.-]+(:\\d+)?(/\\S*)?$";
    public static final Pattern URL_HTTP_PATTERN = Pattern.compile(URL_HTTP);

    /** 소문자 슬러그 (예: my-cert-exam-2025) */
    public static final String SLUG = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
    public static final Pattern SLUG_PATTERN = Pattern.compile(SLUG);

    /** 사용자명: 영문/숫자/밑줄/대시, 3~32자 */
    public static final String USERNAME = "^[A-Za-z0-9_-]{3,32}$";
    public static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME);

    /** 대문자 영숫자와 대시만(코드값 등) */
    public static final String UPPER_CODE = "^[A-Z0-9-]+$";
    public static final Pattern UPPER_CODE_PATTERN = Pattern.compile(UPPER_CODE);

    /** UUID v1~v5 */
    public static final String UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final Pattern UUID_PATTERN = Pattern.compile(UUID);

    /** 공백(스페이스 포함) 하나 이상 */
    public static final String WHITESPACE = "\\s+";
    public static final Pattern WHITESPACE_PATTERN = Pattern.compile(WHITESPACE);

    /** 영문/숫자 외 특수문자 1개 이상 (강도 체크 보조) */
    public static final String HAS_SPECIAL = ".*[^A-Za-z0-9].*";
    public static final Pattern HAS_SPECIAL_PATTERN = Pattern.compile(HAS_SPECIAL);
}
