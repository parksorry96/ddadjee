package com.ddadjee.iam.application.port.out;

/**
 * 비밀번호 hashing port
 */
public interface PasswordHasherPort {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
