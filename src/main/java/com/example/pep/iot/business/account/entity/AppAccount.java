package com.example.pep.iot.business.account.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * app账号->每个brand对应一个app account
 *
 * @author LiuGang
 * @since 2022-03-04 5:05 PM
 */
@Data
@Accessors(chain = true)
@TableName(value = "app_account")
@EqualsAndHashCode(callSuper = true)
public class AppAccount extends BaseEntity {

    /**
     * 厂牌
     */
    @TableField(value = "brandCode")
    private String brandCode;

    //appKey
    @TableField(value = "appKey")
    private String appKey;

    //appSecret
    @TableField(value = "appSecret")
    private String appSecret;


}
