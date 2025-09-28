package com.ddadjee.iam.application.port.in;

/**
 * 로그인 Use Case 인터페이스.
 */
public interface LoginUserUseCase {

    /**
     * 사용자 로그인 처리.
     *
     * @param command 로그인 파라미터
     * @return 로그인 결과 및 토큰 페어
     */
    LoginUserResult login(LoginUserCommand command);
}
