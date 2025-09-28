package com.ddadjee.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * JWT 발급 설정 프로퍼티.
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String issuer = "ddadjee";
    private Duration accessTokenExpiration = Duration.ofMinutes(30);
    private Duration refreshTokenExpiration = Duration.ofDays(7);
    private String keyId;
    private Set<String> defaultScopes = new LinkedHashSet<>(List.of("openid", "profile"));

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Duration getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(Duration accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public Duration getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(Duration refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Set<String> getDefaultScopes() {
        return defaultScopes;
    }

    public void setDefaultScopes(Set<String> defaultScopes) {
        if (defaultScopes == null || defaultScopes.isEmpty()) {
            this.defaultScopes = new LinkedHashSet<>();
            return;
        }
        this.defaultScopes = new LinkedHashSet<>(defaultScopes);
    }
}
