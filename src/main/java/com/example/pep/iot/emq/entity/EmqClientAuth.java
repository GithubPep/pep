package com.example.pep.iot.emq.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author LiuGang
 * @since 2022-02-17 3:12 PM
 */
@Data
@Getter
@Setter
@Accessors(chain = true)
public class EmqClientAuth {

    private Integer id;

    private String deviceCode;

    private String clientId;

    private String serviceAddress;

    private String servicePort;

    private String username;

    private String password;
}

