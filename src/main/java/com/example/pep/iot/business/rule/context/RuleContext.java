package com.example.pep.iot.business.rule.context;

import com.example.pep.iot.business.rule.dto.PassTimeRuleDto;
import com.example.pep.iot.business.rule.entity.PassRule;
import com.example.pep.iot.business.rule.entity.RulePersonSummary;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 规则的上下文对象
 *
 * @author LiuGang
 * @since 2022-03-02 3:51 PM
 */
@Data
@Accessors(chain = true)
public class RuleContext {

    //当前设备
    private String deviceCode;

    //当前下发流水号
    private String flowNo;

    //规则
    private PassRule passRule;

    //当前人员信息
    private Map<String, RulePersonSummary> currentPersonSummariesMap;

    //设备增量
    private List<Collection<String>> deviceCompareRes;

    //人员增量
    private List<Collection<String>> personCompareRes;

    //时间规则
    private List<PassTimeRuleDto> timeRules;
}
