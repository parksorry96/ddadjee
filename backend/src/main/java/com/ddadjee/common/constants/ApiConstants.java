package com.ddadjee.common.constants;

/**
 * API 전역 상수.
 */
public final class ApiConstants {

    private ApiConstants() {}

    // 경로
    public static final String API_BASE_PATH = "/api";
    public static final String SWAGGER_UI_PATH = "/swagger";   // application.yml과 일치
    public static final String OPENAPI_DOCS_PATH = "/api-docs"; // application.yml과 일치

    // 페이지네이션 기본값
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
}

