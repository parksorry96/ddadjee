package com.ddadjee.iam.application.port.in;

/**
 * 회원가입 커맨드 객체
 * @param email
 * @param username
 * @param rawPassword
 */
public record RegisterUserCommand(
        String email,
        String username,
        String rawPassword
) {
}
