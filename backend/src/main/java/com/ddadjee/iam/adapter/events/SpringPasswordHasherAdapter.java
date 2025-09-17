package com.ddadjee.iam.adapter.events;

import com.ddadjee.iam.application.port.out.PasswordHasherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * PasswordEncoder
 */
@Component
@RequiredArgsConstructor
public class SpringPasswordHasherAdapter implements PasswordHasherPort {
    private final PasswordEncoder encoder;

    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
