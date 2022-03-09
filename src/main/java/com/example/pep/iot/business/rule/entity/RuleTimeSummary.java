package com.example.pep.iot.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 规则与时间的关联
 *
 * @author LiuGang
 * @since 2022-03-01 10:05 AM
 */
@Data
@TableName(value = "rule_time_summary")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class  RuleTimeSummary extends BaseEntity {

    @TableField(value = "timeRuleName")
    private String timeRuleName;

    @TableField(value = "ruleId")
    private String ruleId;

    //生效星期(1-7),多选以逗号隔开
    @TableField(value = "effectiveWeek")
    private String effectiveWeek;

    //0.长期 1.自定义
    @TableField(value = "timeRuleType")
    private Integer timeRuleType;

    //自定义开始时间
    @TableField(value = "dayBeginTime")
    private String dayBeginTime;

    //自定义结束时间
    @TableField(value = "dayEndTime")
    private String dayEndTime;

    //生效时间段
    @TableField(value = "effectiveBeginTime")
    private String effectiveBeginTime;

    //生效时间段
    @TableField(value = "effectiveEndTime")
    private String effectiveEndTime;

    //通行许可 1.允许 0.不允许
    @TableField(value = "permitType")
    private Integer permitType;

}
