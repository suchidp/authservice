package com.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtEncryption {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public String encodeSecretKey() {
        byte[] secretKeyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKeyBytes);
        return encodedSecretKey;
    }
}
