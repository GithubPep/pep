package com.example.pep.iot.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 规则与person的关联
 *
 * @author LiuGang
 * @since 2022-03-01 9:46 AM
 */
@Data
@Accessors(chain = true)
@TableName(value = "rule_person_summary")
@EqualsAndHashCode(callSuper = true)
public class RulePersonSummary extends BaseEntity {


    //规则id
    @TableField(value = "ruleId")
    private String ruleId;

    //人员信息id
    @TableField(value = "personId")
    private String personId;

}