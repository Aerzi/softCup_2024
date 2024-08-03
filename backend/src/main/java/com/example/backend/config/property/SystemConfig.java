package com.example.backend.config.property;

import com.example.backend.config.property.judge0.Judge0CreateConfig;
import com.example.backend.config.property.judge0.Judge0GetConfig;
import com.example.backend.config.property.judge0.Judge0LangConfig;
import com.example.backend.config.property.judge0.Judge0StatusConfig;
import com.example.backend.config.property.spark.AIRewriteConfig;
import com.example.backend.config.property.spark.SparkImgConfig;
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

    @Autowired
    private OkHttpConfig okHttpConfig;

    @Autowired
    private Judge0CreateConfig judge0CreateConfig;

    @Autowired
    private Judge0GetConfig judge0GetConfig;

    @Autowired
    private Judge0StatusConfig judge0StatusConfig;

    @Autowired
    private Judge0LangConfig judge0LangConfig;

    @Autowired
    private WebSocketPropertyConfig webSocketPropertyConfig;

    @Autowired
    private SparkImgConfig sparkImgConfig;

    @Autowired
    private AIRewriteConfig aiRewriteConfig;
}
