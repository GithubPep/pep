package com.example.pep.iot.business.rule.dto;

import lombok.Data;

/**
 * @author LiuGang
 * @since 2022-03-02 3:08 PM
 */
@Data
public class PassTimeRuleDto {

    private Long timestamp;

    private String timeRuleId;

    private String timeRuleName;

    private Integer timeRuleType;

    private String dayBeginTime;

    private String dayEndTime;

    private String effectiveWeek;

    private String effectiveBeginTime;

    private String effectiveEndTime;

    private Integer permitType;
}
