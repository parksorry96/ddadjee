package com.ddadjee.iam.application.port.out;

import com.ddadjee.iam.domain.User;

/**
 * 사용자 저장 Port
 */
public interface SaveUserPort {
    User save(User user);
}
