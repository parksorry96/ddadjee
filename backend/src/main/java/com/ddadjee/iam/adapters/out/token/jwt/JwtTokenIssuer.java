package com.ddadjee.iam.adapters.out.token.jwt;

import com.ddadjee.common.config.properties.JwtProperties;
import com.ddadjee.common.constants.SecurityConstants;
import com.ddadjee.iam.application.port.in.LoginUserResult.AuthTokens;
import com.ddadjee.iam.application.port.out.TokenIssuerPort;
import com.ddadjee.iam.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * JWT 기반 토큰 발급 어댑터.
 */
@Component
@Profile("oidc")
@RequiredArgsConstructor
public class JwtTokenIssuer implements TokenIssuerPort {

    private static final String TOKEN_TYPE_BEARER = "Bearer";

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;
    private final Clock clock;

    @Override
    public AuthTokens issueTokens(User user) {
        Instant now = Instant.now(clock);
        Instant accessExpiresAt = now.plus(jwtProperties.getAccessTokenExpiration());
        Instant refreshExpiresAt = now.plus(jwtProperties.getRefreshTokenExpiration());
        Set<String> scopes = new LinkedHashSet<>(jwtProperties.getDefaultScopes());
        String scopeClaim = String.join(" ", scopes);

        String accessToken = encodeToken(now, accessExpiresAt, user, scopeClaim, TokenKind.ACCESS);
        String refreshToken = encodeToken(now, refreshExpiresAt, user, scopeClaim, TokenKind.REFRESH);

        return new AuthTokens(
                accessToken,
                accessExpiresAt,
                refreshToken,
                refreshExpiresAt,
                TOKEN_TYPE_BEARER,
                scopes
        );
    }

    private String encodeToken(
            Instant issuedAt,
            Instant expiresAt,
            User user,
            String scopeClaim,
            TokenKind tokenKind
    ) {
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(user.getId().toString())
                .claim(SecurityConstants.CLAIM_EMAIL, user.getEmail().getValue())
                .claim(SecurityConstants.CLAIM_USERNAME, user.getUsername().getValue())
                .claim(SecurityConstants.CLAIM_SCOPE, scopeClaim)
                .claim("token_type", tokenKind.value);

        if (tokenKind == TokenKind.REFRESH) {
            claimsBuilder.claim("typ", "Refresh");
        }

        JwtClaimsSet claimsSet = claimsBuilder.build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private enum TokenKind {
        ACCESS("access"),
        REFRESH("refresh");

        private final String value;

        TokenKind(String value) {
            this.value = value;
        }
    }
}
