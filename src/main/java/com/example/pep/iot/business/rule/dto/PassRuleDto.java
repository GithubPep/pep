package com.example.pep.iot.business.rule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author LiuGang
 * @since 2022-03-02 2:58 PM
 */
@Data
@Accessors(chain = true)
public class PassRuleDto {

    private String ruleCode;

    private String ruleName;

    private String recognizeType;

    private List<String> delPersons;

    private List<PassPersonDto> savePersons;

    private List<PassTimeRuleDto> timeRules;

    private Long timestamp;
    /**
     网关子设备设备编码
     */
    private List<DeviceCodeDto> devices;
}
