package com.example.backend.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @description The type Web mvc configuration.
 * @author feixia0g
 * @date 2024/7/7 9:50
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "system.qn")
public class QnConfig {
    /**
     * -- GETTER --
     *  Gets url.
     *
     *
     * -- SETTER --
     *  Sets url.
     *
     @return the url
      * @param url the url
     */
    private String url;
    /**
     * -- GETTER --
     *  Gets bucket.
     *
     *
     * -- SETTER --
     *  Sets bucket.
     *
     @return the bucket
      * @param bucket the bucket
     */
    private String bucket;
    /**
     * -- GETTER --
     *  Gets access key.
     *
     *
     * -- SETTER --
     *  Sets access key.
     *
     @return the access key
      * @param accessKey the access key
     */
    private String accessKey;
    /**
     * -- GETTER --
     *  Gets secret key.
     *
     *
     * -- SETTER --
     *  Sets secret key.
     *
     @return the secret key
      * @param secretKey the secret key
     */
    private String secretKey;

}
