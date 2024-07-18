package com.example.backend.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.websocket")
@Getter
@Setter
public class WebSocketPropertyConfig {
    private String chainUrl;
    private String assessUrl;
}
