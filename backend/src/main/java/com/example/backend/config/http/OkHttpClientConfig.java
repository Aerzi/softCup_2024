package com.example.backend.config.http;

import com.example.backend.config.property.SystemConfig;
import okhttp3.OkHttpClient;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpClientConfig {
    private final SystemConfig systemConfig;

    @Autowired
    public OkHttpClientConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(systemConfig.getOkHttpConfig().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(systemConfig.getOkHttpConfig().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(systemConfig.getOkHttpConfig().getWriteTimeout(),TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(systemConfig.getOkHttpConfig().getMaxIdleConnections(), systemConfig.getOkHttpConfig().getKeepAliveDuration(), TimeUnit.SECONDS);
    }
}