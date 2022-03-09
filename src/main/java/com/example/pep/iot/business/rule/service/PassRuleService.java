package com.example.pep.iot.business.rule.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pep.iot.business.rule.entity.PassRule;
import com.example.pep.iot.business.rule.entity.RuleDeviceSummary;
import com.example.pep.iot.business.rule.entity.RulePersonSummary;
import com.example.pep.iot.business.rule.entity.RuleTimeSummary;
import com.example.pep.iot.business.rule.mapper.PassRuleMapper;
import com.example.pep.iot.business.rule.req.RuleDeviceReq;
import com.example.pep.iot.business.rule.req.RuleReq;
import com.example.pep.iot.business.rule.req.RuleTimeReq;
import com.example.pep.iot.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 规则主逻辑
 *
 * @author LiuGang
 * @since 2022-02-28 8:50 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PassRuleService extends ServiceImpl<PassRuleMapper, PassRule> {

    private final RulePersonService rulePersonService;

    private final RuleDeviceService ruleDeviceService;

    private final RuleTimeService ruleTimeService;

    /**
     * 添加rule todo 完善分组
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRule(RuleReq ruleReq) {
        PassRule passRule = BeanUtil.copyProperties(ruleReq, PassRule.class);
        String recognizeType = String.join(",", ruleReq.getRecognizeTypes());
        passRule.setRecognizeType(recognizeType);
        passRule.setPublishTime(LocalDateTime.now());
        if (!CollectionUtils.isEmpty(ruleReq.getRulePersons())) {
            //按照人员下发
            passRule.setUserCount(ruleReq.getRulePersons().size());
            passRule.setRuleType(0);
            this.save(passRule);
            List<RulePersonSummary> rulePersonSummaries = batchInsertPerson(passRule.getId(), ruleReq.getRulePersons());
            rulePersonService.saveBatch(rulePersonSummaries);
        }
        List<RuleDeviceSummary> ruleDeviceSummaries = batchInsertDevice(passRule.getId(), ruleReq.getRuleDevices());
        ruleDeviceService.saveBatch(ruleDeviceSummaries);
        List<RuleTimeSummary> ruleTimeSummaries = batchInsertTimeRule(passRule.getId(), ruleReq.getPassTimeRules());
        ruleTimeService.saveBatch(ruleTimeSummaries);
    }

    //批量处理
    private List<RuleTimeSummary> batchInsertTimeRule(String ruleId, List<RuleTimeReq> passTimeRules) {
        return StreamUtils.stream(passTimeRules).map(item -> {
            RuleTimeSummary ruleDeviceSummary = BeanUtil.copyProperties(item, RuleTimeSummary.class);
            return ruleDeviceSummary.setRuleId(ruleId);
        }).collect(Collectors.toList());
    }

    //批量处理rule-device
    private List<RuleDeviceSummary> batchInsertDevice(String ruleId, List<RuleDeviceReq> ruleDevices) {
        return StreamUtils.stream(ruleDevices).map(item -> {
            RuleDeviceSummary ruleDeviceSummary = BeanUtil.copyProperties(item, RuleDeviceSummary.class);
            return ruleDeviceSummary.setRuleId(ruleId).setFlowNo(IdUtil.getSnowflakeNextIdStr());
        }).collect(Collectors.toList());
    }

    //批量新增rule-person
    private List<RulePersonSummary> batchInsertPerson(String ruleId, List<String> rulePersons) {
        return StreamUtils.stream(rulePersons)
                .map(personId -> new RulePersonSummary().setRuleId(ruleId).setPersonId(personId))
                .collect(Collectors.toList());
    }

    List<RuleDeviceSummary> getRuleDeviceSummaryByRuleId(String ruleId) {
        LambdaQueryWrapper<RuleDeviceSummary> ruleDeviceSummaryQueryWrapper = Wrappers.lambdaQuery(RuleDeviceSummary.class);
        ruleDeviceSummaryQueryWrapper.eq(RuleDeviceSummary::getRuleId, ruleId);
        return ruleDeviceService.list(ruleDeviceSummaryQueryWrapper);
    }

    List<RulePersonSummary> getRulePersonSummaryByRuleId(String ruleId) {
        LambdaQueryWrapper<RulePersonSummary> rulePersonSummaryQueryWrapper = Wrappers.lambdaQuery(RulePersonSummary.class);
        rulePersonSummaryQueryWrapper.eq(RulePersonSummary::getRuleId, ruleId);
        return rulePersonService.list(rulePersonSummaryQueryWrapper);
    }

    List<RuleTimeSummary> getRuleTimeSummary(String ruleId) {
        LambdaQueryWrapper<RuleTimeSummary> ruleTimeSummaryQueryWrapper = Wrappers.lambdaQuery(RuleTimeSummary.class);
        ruleTimeSummaryQueryWrapper.eq(RuleTimeSummary::getRuleId, ruleId);
        return ruleTimeService.list(ruleTimeSummaryQueryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delRule(List<String> ids) {
        this.removeBatchByIds(ids);
        this.ruleDeviceService.removeBatchByIds(ids);
        this.ruleTimeService.removeBatchByIds(ids);
        this.rulePersonService.removeBatchByIds(ids);
    }

    /**
     * 查询规则细节
     *
     * @param id 规则id
     * @return 规则详细信息
     */
    public RuleReq queryRuleDetail(String id) {
        PassRule passRule = this.getById(id);
        RuleReq ruleReq = BeanUtil.copyProperties(passRule, RuleReq.class);
        ruleReq.setRecognizeTypes(Arrays.asList(passRule.getRecognizeType().split(",")));
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("ruleId", id);
        List<RuleDeviceSummary> ruleDeviceSummaries = this.ruleDeviceService.listByMap(columnMap);
        List<RulePersonSummary> rulePersonSummaries = this.rulePersonService.listByMap(columnMap);
        List<RuleTimeSummary> ruleTimeSummaries = this.ruleTimeService.listByMap(columnMap);
        ruleReq.setRuleDevices(copyProperties(RuleDeviceReq.class, ruleDeviceSummaries));
        ruleReq.setRulePersons(StreamUtils.stream(rulePersonSummaries).map(RulePersonSummary::getPersonId).collect(Collectors.toList()));
        ruleReq.setPassTimeRules(copyProperties(RuleTimeReq.class, ruleTimeSummaries));
        return ruleReq;

    }

    private <Target, Source> List<Target> copyProperties(Class<Target> tClass, List<Source> sourceList) {
        return StreamUtils.stream(sourceList)
                .map(item -> BeanUtil.copyProperties(item, tClass))
                .collect(Collectors.toList());
    }
}
