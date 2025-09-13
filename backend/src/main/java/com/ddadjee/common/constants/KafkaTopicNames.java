package com.ddadjee.common.constants;

/**
 * Kafka 토픽 이름(외부 연계용).
 * <p>
 * - 실제 운영 시에는 환경별 프리픽스/네임스페이스를 붙이거나, 설정으로 주입하도록 확장할 수 있습니다.
 */
public final class KafkaTopicNames {

    private KafkaTopicNames() {}

    // 카탈로그/일정/지갑 등 도메인 이벤트 토픽
    public static final String CERTIFICATION_CATALOG_UPSERT = "cert.catalog.upsert";
    public static final String EXAM_SCHEDULE_UPSERT = "exam.schedule.upsert";
    public static final String USER_WALLET_LINKED = "user.wallet.linked";
    public static final String APPLICATION_CREATED = "application.created";
}

