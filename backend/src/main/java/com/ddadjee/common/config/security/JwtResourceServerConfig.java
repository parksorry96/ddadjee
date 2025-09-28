package com.ddadjee.common.config.security;

import com.ddadjee.common.config.properties.JwtProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@Profile("oidc")
@EnableConfigurationProperties(JwtProperties.class)
public class JwtResourceServerConfig {

    @Bean
    public RSAPublicKey jwtPublicKey(
            @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}") Resource publicKeyLocation
    ) throws IOException {
        try(InputStream inputStream = publicKeyLocation.getInputStream()) {
            return (RSAPublicKey) RsaKeyConverters.x509().convert(inputStream);
        }
    }

    @Bean
    public RSAPrivateKey jwtPrivateKey(
            @Value("${jwt.private-key-location}") Resource privateKeyLocation
    ) throws IOException {
        try(InputStream inputStream = privateKeyLocation.getInputStream()) {
            return  (RSAPrivateKey) RsaKeyConverters.pkcs8().convert(inputStream);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey)
                .signatureAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder(RSAPublicKey publicKey,
                                 RSAPrivateKey privateKey,
                                 @Value("${jwt.key-id}") String keyId) {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(keyId)
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));

        return new NimbusJwtEncoder(jwkSource);
    }

}
