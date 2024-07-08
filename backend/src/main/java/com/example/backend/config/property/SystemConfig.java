package com.example.backend.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version 1.0
 * @description The type Web mvc configuration.
 * @author feixia0g
 * @date 2024/7/7 9:50
 */
@Component
@ConfigurationProperties(prefix = "system")
@Getter
@Setter
public class SystemConfig {

    @Autowired
    private PasswordKeyConfig passwordKeyConfig;

    private List<String> securityIgnoreUrls;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private QnConfig qn;
}
