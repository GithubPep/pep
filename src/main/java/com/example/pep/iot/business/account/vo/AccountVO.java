package com.example.pep.iot.business.account.vo;

import lombok.Data;

/**
 * vo对象
 *
 * @author LiuGang
 * @since 2022-03-26 21:37
 */
@Data
public class AccountVO {

    //id
    private String id;

    /**
     * 厂牌
     */
    private String brandCode;

    //appKey
    private String appKey;

    //appSecret
    private String appSecret;
}
