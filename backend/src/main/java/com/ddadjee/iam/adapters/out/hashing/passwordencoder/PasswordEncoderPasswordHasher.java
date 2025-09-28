package com.ddadjee.iam.adapters.out.hashing.passwordencoder;

import com.ddadjee.iam.application.port.out.PasswordHasherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * PasswordHasherPort 구현(PasswordEncoder 위임).
 * 기술명은 패키지로 표현하고, 클래스명은 역할 중심으로 명명합니다.
 */
@Component
@RequiredArgsConstructor
public class PasswordEncoderPasswordHasher implements PasswordHasherPort {
    private final PasswordEncoder encoder;

    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
