package com.example.pep.iot.emq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * emq注册配置
 *
 * @author LiuGang
 * @since 2022-02-17 2:02 PM
 */
@Component
@Data
@ConfigurationProperties(prefix = "pep.emq")
public class EmqAuthConfig {

    private String username;

    private String password;

    private String httpUrl;

    private String clientAuthUrl;

    private Integer keepAlive;

}
