package com.example.pep.iot.business.rule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 通行规则的payload
 *
 * @author LiuGang
 * @since 2022-03-02 2:50 PM
 */
@Data
@Accessors(chain = true)
public class PassRulePayload {

    private String deviceCode;

    private String reqId;

    private List<PassRuleDto> saveRules;

    private List<String> delRules;
}
