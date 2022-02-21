package com.example.pep.iot.emq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * emq发送mqtt消息配置
 *
 * @author LiuGang
 * @since 2022-02-17 2:35 PM
 */
@Data
@Component
@ConfigurationProperties(prefix = "pep.emq.mqtt")
public class EmqMqttConfig {

    private String mqttUrl;

    private String port;
}





