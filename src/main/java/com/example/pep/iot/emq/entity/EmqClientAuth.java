package com.example.pep.iot.emq.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author LiuGang
 * @since 2022-02-17 3:12 PM
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "emq_client_auth")
public class EmqClientAuth extends BaseEntity {

    @TableField(value = "deviceCode")
    private String deviceCode;

    @TableField(value = "clientId")
    private String clientId;

    @TableField(value = "serviceAddress")
    private String serviceAddress;

    @TableField(value = "servicePort")
    private String servicePort;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;
}
