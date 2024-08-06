package com.example.backend.config.property.spark;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spark.ai-ppt")
@Getter
@Setter
public class AIPPTConfig {
    private String baseUrl;

    private String appId;

    private String secret;
}
