package com.example.pep.iot.business.rule.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.example.pep.iot.business.rule.context.RuleContext;
import com.example.pep.iot.business.rule.dto.PassRulePayload;
import com.example.pep.iot.business.rule.dto.PassTimeRuleDto;
import com.example.pep.iot.business.rule.entity.*;
import com.example.pep.iot.business.rule.handler.ParamHandler;
import com.example.pep.iot.mqtt.MqttProducer;
import com.example.pep.iot.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 发布主逻辑
 *
 * @author LiuGang
 * @since 2022-03-01 10:50 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PublishService {

    private final PassRuleService passRuleService;

    private final RulePublishSnapshotService rulePublishSnapshotService;

    private final MqttProducer producer;

    private final ParamHandler paramHandler;

    /**
     * 发布
     *
     * @param ids 规则ids
     */
    public void publish(List<String> ids) {
        List<PassRule> passRules = passRuleService.listByIds(ids);
        Map<String, PassRulePayload> payloadMap = new HashMap<>();
        passRules.forEach(passRule -> {
            List<RuleDeviceSummary> ruleDeviceSummaries = passRuleService.getRuleDeviceSummaryByRuleId(passRule.getId());
            Map<String, String> deviceCodeFlowNoMap = new HashMap<>();
            RulePublishSnapshot rulePublishSnapshot = rulePublishSnapshotService.getById(passRule.getId());
            List<Collection<String>> deviceCalculate = calculateDevice(ruleDeviceSummaries, rulePublishSnapshot, deviceCodeFlowNoMap);
            List<RulePersonSummary> rulePersonSummaries = passRuleService.getRulePersonSummaryByRuleId(passRule.getId());
            List<Collection<String>> personCalculate = calculatePerson(rulePersonSummaries, rulePublishSnapshot);
            List<RuleTimeSummary> ruleTimeSummary = passRuleService.getRuleTimeSummary(passRule.getId());
            List<PassTimeRuleDto> timeRules = copyRuleTimeDto(ruleTimeSummary);
            Map<String, RulePersonSummary> personSummaryMap = StreamUtils.stream(rulePersonSummaries).collect(Collectors.toMap(RulePersonSummary::getPersonId, Function.identity()));
            RuleContext ruleContext = new RuleContext().setPassRule(passRule).setDeviceCompareRes(deviceCalculate).setPersonCompareRes(personCalculate).setTimeRules(timeRules).setCurrentPersonSummariesMap(personSummaryMap);
            //处理当前规则下的设备
            ruleDeviceSummaries.forEach(ruleDeviceSummary -> {
                ruleContext.setDeviceCode(ruleDeviceSummary.getDeviceCode()).setFlowNo(ruleDeviceSummary.getFlowNo());
                paramHandler.generateRegularParam(ruleContext, payloadMap);
            });
            //处理需要删除的设备
            Collection<String> delDevices = deviceCalculate.get(1);
            if (CollectionUtil.isNotEmpty(delDevices)) {
                delDevices.forEach(delItem -> {
                    String flowNo = deviceCodeFlowNoMap.get(delItem);
                    ruleContext.setDeviceCode(delItem).setFlowNo(flowNo);
                    paramHandler.generateRegularParam(ruleContext, payloadMap);
                });
            }
        });
        publishMqtt(payloadMap);
    }

    /**
     * 使用mqttProduct 发布到emq
     *
     * @param payloadMap 参数
     */
    private void publishMqtt(Map<String, PassRulePayload> payloadMap) {

        // todo    producer.sendMsg();
    }

    //copy
    private List<PassTimeRuleDto> copyRuleTimeDto(List<RuleTimeSummary> ruleTimeSummary) {
        return StreamUtils.stream(ruleTimeSummary).map(item -> BeanUtil.copyProperties(item, PassTimeRuleDto.class)).collect(Collectors.toList());
    }

    private List<Collection<String>> calculatePerson(List<RulePersonSummary> rulePersonSummaries, RulePublishSnapshot rulePublishSnapshot) {
        List<String> current = StreamUtils.stream(rulePersonSummaries).map(RulePersonSummary::getPersonId).collect(Collectors.toList());
        List<String> snapshot = new ArrayList<>();
        if (Objects.nonNull(rulePublishSnapshot) && StrUtil.isNotBlank(rulePublishSnapshot.getPersons())) {
            String[] split = rulePublishSnapshot.getPersons().split(",");
            snapshot = Arrays.asList(split);
        }
        return compareByCollection(current, snapshot, null);
    }

    private List<Collection<String>> calculateDevice(List<RuleDeviceSummary> ruleDeviceSummaries,
                                                     RulePublishSnapshot rulePublishSnapshot,
                                                     Map<String, String> deviceCodeFlowNoMap) {
        List<String> current = StreamUtils.stream(ruleDeviceSummaries).map(RuleDeviceSummary::getDeviceCode).collect(Collectors.toList());
        List<String> snapshot = new ArrayList<>();
        if (Objects.nonNull(rulePublishSnapshot) && StrUtil.isNotBlank(rulePublishSnapshot.getDeviceCodes())) {
            String[] devices = rulePublishSnapshot.getDeviceCodes().split(",");
            String[] flows = rulePublishSnapshot.getFlowNos().split(",");
            for (int i = 0; i < devices.length; i++) {
                deviceCodeFlowNoMap.put(devices[i], flows[i]);
            }
            snapshot = Arrays.asList(devices);
        }
        return compareByCollection(current, snapshot, null);
    }

    private List<Collection<String>> compareByCollection(Collection<String> currentCollections, Collection<String> snapshotCollections, Predicate<String> predicate) {
        //交集
        Collection<String> intersection = CollectionUtil.intersection(currentCollections, snapshotCollections);
        //add
        Collection<String> addRuleCodes = CollectionUtil.disjunction(currentCollections, intersection);
        //del
        Collection<String> delRuleCodes = CollectionUtil.disjunction(snapshotCollections, intersection);
        if (Objects.nonNull(predicate)) {
            intersection = intersection.stream().filter(predicate).collect(Collectors.toList());
        }
        List<Collection<String>> res = new ArrayList<>(3);
        res.add(addRuleCodes);
        res.add(delRuleCodes);
        res.add(intersection);
        return res;
    }


    private <LOCAL_VALUE, CLOUD_VALUE> List<Collection<String>> compare(Map<String, LOCAL_VALUE> localMap, Map<String, CLOUD_VALUE> cloudMap, Predicate<String> predicate) {
        Set<String> cloudSet = cloudMap.keySet();
        Set<String> localSet = localMap.keySet();
        return compareByCollection(localSet, cloudSet, predicate);
    }

    /**
     * 自动发布
     */
    public void autoPublish() {

    }
}
