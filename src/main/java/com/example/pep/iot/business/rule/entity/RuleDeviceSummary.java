package com.example.pep.iot.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 规则与设备的关联信息
 *
 * @author LiuGang
 * @since 2022-03-01 9:49 AM
 */
@Data
@TableName(value = "rule_device_summary")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RuleDeviceSummary extends BaseEntity {

    //规则id
    @TableField(value = "ruleId")
    private String ruleId;

    //设备id
    @TableField(value = "deviceId")
    private String deviceId;

    //设备编码
    @TableField(value = "deviceCode")
    private String deviceCode;

    //下发流水号
    @TableField(value = "flowNo")
    private String flowNo;

}
