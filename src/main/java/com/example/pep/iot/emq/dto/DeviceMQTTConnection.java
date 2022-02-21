package com.example.pep.iot.emq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * emq的通信消息
 *
 * @author LiuGang
 * @since 2022-02-17 11:22 AM
 */
@Data
@Accessors(chain = true)
public class DeviceMQTTConnection {

    //ip
    private String ip;

    //port
    private int port;

    //username
    private String username;

    //password
    private String password;

    //keepAlive
    private int keepAlive;
}
