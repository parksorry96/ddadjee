package com.ddadjee.iam.application.port.out;

import com.ddadjee.iam.domain.Email;
import com.ddadjee.iam.domain.User;
import java.util.Optional;

/**
 * 사용자 조회 포트(Email 중복 검사)
 */
public interface LoadUserPort {
    Optional<User> findByEmail(Email email);
}
