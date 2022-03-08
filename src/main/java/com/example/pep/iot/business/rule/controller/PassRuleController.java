package com.example.pep.iot.business.rule.controller;

import com.example.pep.iot.business.rule.req.RuleReq;
import com.example.pep.iot.business.rule.service.PassRuleService;
import com.example.pep.iot.business.rule.service.PublishService;
import com.example.pep.iot.response.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规则控制器
 *
 * @author LiuGang
 * @since 2022-02-28 8:48 PM
 */
@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class PassRuleController {

    private final PassRuleService passRuleService;

    private final PublishService publishService;

    @PostMapping("/add")
    public ResultVO<Void> addRule(@RequestBody RuleReq ruleReq) {
        passRuleService.addRule(ruleReq);
        return ResultVO.ok();
    }

    @PostMapping("/publish")
    public ResultVO<Void> publish(List<String> ids) {
        publishService.publish(ids);
        return new ResultVO<>();
    }

}
