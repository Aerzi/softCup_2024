package com.example.backend.config.property.spark;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spark.ai-translation")
@Getter
@Setter
public class AITranslationConfig {
    private String requestUrl;

    private String appId;

    private String apiSecret;

    private String apiKey;

    private String resId;
}
