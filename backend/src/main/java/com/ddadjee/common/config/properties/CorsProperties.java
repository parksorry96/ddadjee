package com.ddadjee.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 전역 CORS 설정 바인딩 프로퍼티.
 * <p>
 * application.yml 의 `app.cors.*` 키로 설정합니다.
 */
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    private List<String> allowedOrigins = new ArrayList<>(List.of("http://localhost:5173"));
    private List<String> allowedMethods = new ArrayList<>(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
    private List<String> allowedHeaders = new ArrayList<>(List.of("*"));
    private boolean allowCredentials = true;
    private long maxAgeSec = 3600;

    public List<String> getAllowedOrigins() { return allowedOrigins; }
    public void setAllowedOrigins(List<String> allowedOrigins) { this.allowedOrigins = allowedOrigins; }

    public List<String> getAllowedMethods() { return allowedMethods; }
    public void setAllowedMethods(List<String> allowedMethods) { this.allowedMethods = allowedMethods; }

    public List<String> getAllowedHeaders() { return allowedHeaders; }
    public void setAllowedHeaders(List<String> allowedHeaders) { this.allowedHeaders = allowedHeaders; }

    public boolean isAllowCredentials() { return allowCredentials; }
    public void setAllowCredentials(boolean allowCredentials) { this.allowCredentials = allowCredentials; }

    public long getMaxAgeSec() { return maxAgeSec; }
    public void setMaxAgeSec(long maxAgeSec) { this.maxAgeSec = maxAgeSec; }
}

