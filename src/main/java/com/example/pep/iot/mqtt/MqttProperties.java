package com.example.pep.iot.mqtt;

import lombok.Data;

/**
 * @author LiuGang
 * @since 2022-03-07 2:32 PM
 */
@Data
public class MqttProperties {

    private String username;

    private String password;

    private String clientId;

    private Boolean cleanSession = false;

    private String host;

    private int connectionTimeout = 10;

    private long completionTimeout = 3000;

    private int[] qos;

    private String[] topic;
}



