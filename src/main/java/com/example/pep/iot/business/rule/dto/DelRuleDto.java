package com.example.pep.iot.business.rule.dto;

import lombok.Data;

import java.util.List;

/**
 * @author LiuGang
 * @since 2022-03-02 3:12 PM
 */
@Data
public class DelRuleDto {

    private String ruleCode;

    private List<DeviceCodeDto> devices;

}
