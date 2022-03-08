package com.example.pep.iot.business.rule.req;

import lombok.Data;

/**
 * 规则-时间-VO对象
 *
 * @author LiuGang
 * @since 2022-03-01 10:03 AM
 */
@Data
public class RuleTimeReq {

    private String timeRuleName;

    private Integer timeRuleType;

    private String dayBeginTime;

    private String dayEndTime;

    private String effectiveWeek;

    private String effectiveBeginTime;

    private String effectiveEndTime;

    private Integer permitType;
}