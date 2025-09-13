package com.ddadjee.common.constants;

import java.util.Arrays;

/**
 * Redis 키 네임스페이스 및 유틸리티.
 * <p>
 * 키 스키마를 통일하기 위해 prefix 기반 네임스페이스를 정의합니다.
 * 예: user:123:wallet, exam:schedule:2025-10
 */
public final class RedisNamespaces {

    private RedisNamespaces() {}

    // 네임스페이스(콜론으로 구분)
    public static final String USER = "user";
    public static final String IAM = "iam";
    public static final String CATALOG = "catalog";
    public static final String EXAM = "exam";
    public static final String SCHEDULE = "schedule";
    public static final String WALLET = "wallet";
    public static final String APPLICATION = "application";
    public static final String RESUME = "resume";
    public static final String COMMUNITY = "community";
    public static final String RECOMMENDATION = "recommendation";

    /**
     * 키 구성 유틸리티. 전달된 파츠를 콜론(':')으로 연결합니다.
     * 사용 예: key(USER, "123", WALLET) → user:123:wallet
     */
    public static String key(String... parts) {
        return String.join(":", Arrays.asList(parts));
    }
}

