package com.example.backend.config.property.judge0;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.judge0.get")
@Getter
@Setter
public class Judge0GetConfig {
    private String urlFront;

    private String urlBack;

    private String XRapidAPIKey;

    private String XRapidAPIHost;
}
