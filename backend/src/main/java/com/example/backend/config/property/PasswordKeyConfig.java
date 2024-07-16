package com.example.backend.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @description: The type Password key config.
 * @author feixia0g
 * @date 2024/7/7 16:30
 */
@Component
@ConfigurationProperties(prefix = "system.pwd-key")
@Getter
@Setter
public class PasswordKeyConfig {
    private String publicKey;

    private String privateKey;
}
