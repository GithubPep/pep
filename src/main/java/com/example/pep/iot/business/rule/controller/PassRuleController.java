package com.example.pep.iot.business.rule.controller;

import com.example.pep.iot.base.ValidGroup;
import com.example.pep.iot.business.rule.req.RuleReq;
import com.example.pep.iot.business.rule.service.PassRuleService;
import com.example.pep.iot.business.rule.service.PublishService;
import com.example.pep.iot.response.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO<Void> addRule(@RequestBody @Validated(value = ValidGroup.ValidAdd.class) RuleReq ruleReq) {
        passRuleService.addRule(ruleReq);
        return ResultVO.ok();
    }

    // todo 简单版本
    @PostMapping("/del")
    public ResultVO<Void> delRule(@RequestBody List<String> ids) {
        passRuleService.delRule(ids);
        return ResultVO.ok();
    }

    @GetMapping("/queryRuleDetail/{id}")
    public ResultVO<RuleReq> queryRuleDetail(@PathVariable("id") String id) {
        RuleReq ruleReq = passRuleService.queryRuleDetail(id);
        return ResultVO.ok(ruleReq);
    }

    @PostMapping("/publish")
    public ResultVO<Void> publish(@RequestBody List<String> ids) {
        publishService.publish(ids);
        return new ResultVO<>();
    }

    //todo 考虑一些会发生自动发布的情况
    @PostMapping("/autoPublish")
    public ResultVO<Void> autoPublish() {
        publishService.autoPublish();
        return new ResultVO<>();
    }

}
