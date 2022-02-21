package com.example.pep.iot.emq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * emq认证返回
 *
 * @author LiuGang
 * @since 2022-02-17 11:03 AM
 */
@Data
@Accessors(chain = true)
public class AuthData {

    private String clientId;

    private String serviceAddress;

    private String servicePort;

    private String username;

    private String password;
}
