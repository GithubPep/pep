package com.example.pep.iot.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * @author LiuGang
 * @since 2022-02-28 8:51 PM
 */
@Data
@TableName(value = "pass_rule")
@EqualsAndHashCode(callSuper = true)
public class PassRule extends BaseEntity {

    //规则名称
    @TableField(value = "ruleName")
    private String ruleName;

    //规则描述
    @TableField(value = "ruleDesc")
    private String ruleDesc;

    //0.按照人员下发 1 按照人员分组下发
    @TableField(value = "ruleType")
    private Integer ruleType = 0;

    //识别方式0.人脸 1.门禁卡 2.二维码,多个以逗号隔开
    @TableField(value = "recognizeType")
    private String recognizeType;

    //状态 0.未下发 1已下发 2 下发中 3 下发失败
    @TableField(value = "status")
    private Integer status = 0;

    //应用人数
    @TableField(value = "userCount")
    private Integer userCount;

    //下发时间
    @TableField(value = "publishTime")
    private LocalDateTime publishTime;

}
