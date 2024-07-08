package com.example.backend.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.jwt")
@Getter
@Setter
public class JwtConfig {
    private String secretKey;

    private int expiration;
}
