package com.example.backend.config.property.spark;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spark.ai-rewrite")
@Getter
@Setter
public class AIRewriteConfig {
    private String requestUrl;

    private String appId;

    private String apiSecret;

    private String apiKey;
}
