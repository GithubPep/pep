package com.example.pep.iot.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 发布快照
 *
 * @author LiuGang
 * @since 2022-03-02 3:29 PM
 */
@Data
@TableName(value = "rule_publish_snapshot")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RulePublishSnapshot extends BaseEntity {

    @TableField(value = "ruleId")
    private String ruleId;

    @TableField(value = "deviceCodes")
    private String deviceCodes;

    @TableField(value = "flowNos")
    private String flowNos;

    @TableField(value = "persons")
    private String persons;

}
