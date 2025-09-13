package com.ddadjee.common.constants;

/**
 * Spring Cache 이름 상수.
 * <p>
 * CacheManager 구성과 @Cacheable/@CacheEvict에서 동일한 이름을 사용해
 * 오타를 방지하고 일관성을 유지합니다.
 */
public final class CacheNames {

    private CacheNames() {}

    // 공용/시스템
    public static final String LOOKUP = "lookup";                   // 코드/상수성 데이터
    public static final String SETTINGS = "settings";               // 환경/설정 캐시

    // 도메인별
    public static final String IAM_USERS = "iam:users";             // 사용자 프로필/권한
    public static final String CATALOG_CERTIFICATIONS = "catalog:certifications"; // 자격증 카탈로그
    public static final String SCHEDULE_EXAMS = "schedule:exams";   // 시험 일정
    public static final String WALLET_OWNERSHIP = "wallet:ownership"; // 보유 자격증 목록
    public static final String APPLICATIONS = "applications";       // 신청 관련 데이터
    public static final String RECOMMENDATION = "recommendation";   // 추천 결과(짧은 TTL)
}

