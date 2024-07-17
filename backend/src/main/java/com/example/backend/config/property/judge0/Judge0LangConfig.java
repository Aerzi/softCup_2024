package com.example.backend.config.property.judge0;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.judge0.lang")
@Getter
@Setter
public class Judge0LangConfig {
    private String url;

    private String XRapidAPIKey;

    private String XRapidAPIHost;
}