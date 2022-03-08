package com.example.pep.iot.business.rule.req;

import lombok.Data;

/**
 * 规则-设备-VO对象
 *
 * @author LiuGang
 * @since 2022-03-01 10:01 AM
 */
@Data
public class RuleDeviceReq {

    private String deviceId;

    private String deviceCode;
}
