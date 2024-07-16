package com.example.backend.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ok.http")
@Getter
@Setter
public class OkHttpConfig {
    private Integer connectTimeout;

    private Integer readTimeout;

    private Integer writeTimeout;

    private Integer maxIdleConnections;

    private Integer keepAliveDuration;
}
