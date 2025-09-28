package com.ddadjee.iam.application.port.out;

import com.ddadjee.iam.application.port.in.LoginUserResult;
import com.ddadjee.iam.domain.User;

/**
 * 인증 토큰 발급 포트.
 * <p>
 * JWT 등 인증 토큰 생성을 애플리케이션 계층에서 추상화합니다.
 */
public interface TokenIssuerPort {

    /**
     * 주어진 사용자에 대해 access/refresh 토큰 페어를 발급합니다.
     *
     * @param user 토큰을 발급할 사용자
     * @return 발급된 토큰 세트
     */
    LoginUserResult.AuthTokens issueTokens(User user);
}
