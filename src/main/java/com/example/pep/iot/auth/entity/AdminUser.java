package com.example.pep.iot.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 管理员
 *
 * @author LiuGang
 * @since 2022-03-22 21:16
 */
@Data
@Accessors(chain = true)
@TableName(value = "admin_user")
@EqualsAndHashCode(callSuper = true)
public class AdminUser extends BaseEntity {

    @TableField(value = "userCode")
    private String userCode;

    @TableField(value = "password")
    private String password;

    @TableField(value = "salt")
    private String salt;

    @TableField(value = "username")
    private String username;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "mail")
    private String mail;

    @TableField(value = "imgPath")
    private String imgPath;

}
