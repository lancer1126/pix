package com.lance.pix.basic.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lancer1126
 * @Date 2020-12-3
 * @Description jjwt配置类
 */
@ConfigurationProperties(prefix = "jjwt")
@Data
public class AuthProperties {
    private String secret;

    private String expirationTime;

    private long refreshInterval;
}