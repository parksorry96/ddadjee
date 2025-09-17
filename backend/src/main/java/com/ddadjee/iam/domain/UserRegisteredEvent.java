package com.ddadjee.iam.domain;


import java.time.Instant;
import java.util.UUID;

/**
 * 회원가입이 완료되었음을 알리는 이벤트
 * @param userId 사용자 ID
 * @param email 사용자 이메일
 * @param username 사용자명(문자)
 * @param occurredAt 이벤트 발생 시각
 */
public record UserRegisteredEvent(
        UUID userId,
        String email,
        String username,
        Instant occurredAt
) {
}
