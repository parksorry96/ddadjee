package com.ddadjee.iam.application.port.in;

/**
 * 로그인 커맨드 객체.
 *
 * @param email 로그인 식별 이메일
 * @param rawPassword 사용자가 입력한 비밀번호
 */
public record LoginUserCommand(
        String email,
        String rawPassword
) {
}
