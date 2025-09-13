package com.ddadjee.common.constants;

/**
 * 보안/인증 관련 상수.
 */
public final class SecurityConstants {

    private SecurityConstants() {}

    // HTTP 헤더
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // JWT 클레임 키(일반적으로 사용되는 표준/커스텀)
    public static final String CLAIM_SUBJECT = "sub";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_USERNAME = "preferred_username";
    public static final String CLAIM_SCOPE = "scope";
    public static final String CLAIM_ROLES = "roles"; // 공급자에 따라 다른 키를 사용하기도 함

    // Redis Key Prefixes
    public static final String RATE_LIMIT_REDIS_KEY_PREFIX = "rl:"; // RateLimiting에서 사용

    // Spring Profiles
    public static final String PROFILE_OIDC = "oidc";
    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_PROD = "prod";
}

