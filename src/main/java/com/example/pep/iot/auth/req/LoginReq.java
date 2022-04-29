package com.example.pep.iot.auth.req;

import lombok.Builder;
import lombok.Data;

/**
 * 登录请求
 *
 * @author LiuGang
 * @since 2022-03-22 15:28
 */
@Data
@Builder
public class LoginReq {

    private String userCode;

    private String password;
}
