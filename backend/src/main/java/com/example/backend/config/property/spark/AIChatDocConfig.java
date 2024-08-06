package com.example.backend.config.property.spark;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spark.ai-chat-doc")
@Getter
@Setter
public class AIChatDocConfig {
    private String uploadUrl;

    private String chatUrl;

    private String summaryStartUrl;

    private String summaryQueryUrl;

    private String appId;

    private String secret;
}
