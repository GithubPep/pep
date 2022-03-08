package com.example.pep.iot.business.rule.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.example.pep.iot.business.rule.context.RuleContext;
import com.example.pep.iot.business.rule.dto.PassRulePayload;
import com.example.pep.iot.business.rule.entity.PassRule;
import com.example.pep.iot.business.rule.entity.RuleDeviceSummary;
import com.example.pep.iot.business.rule.entity.RulePersonSummary;
import com.example.pep.iot.business.rule.entity.RulePublishSnapshot;
import com.example.pep.iot.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
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
            RulePublishSnapshot rulePublishSnapshot = rulePublishSnapshotService.getById(passRule.getId());
            List<Collection<String>> deviceCalculate = calculateDevice(ruleDeviceSummaries, rulePublishSnapshot);
            List<RulePersonSummary> rulePersonSummaries = passRuleService.getRulePersonSummaryByRuleId(passRule.getId());
            List<Collection<String>> personCalculate = calculatePerson(rulePersonSummaries, rulePublishSnapshot);

            RuleContext ruleContext = new RuleContext()
                    .setPassRule(passRule)
                    .setDeviceCompareRes(deviceCalculate)
                    .setPersonCompareRes(personCalculate);

        });

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

    private List<Collection<String>> calculateDevice(List<RuleDeviceSummary> ruleDeviceSummaries, RulePublishSnapshot rulePublishSnapshot) {
        List<String> current = StreamUtils.stream(ruleDeviceSummaries).map(RuleDeviceSummary::getDeviceId).collect(Collectors.toList());
        List<String> snapshot = new ArrayList<>();
        if (Objects.nonNull(rulePublishSnapshot) && StrUtil.isNotBlank(rulePublishSnapshot.getDeviceCodes())) {
            String[] split = rulePublishSnapshot.getDeviceCodes().split(",");
            snapshot = Arrays.asList(split);
        }
        return compareByCollection(current, snapshot, null);
    }

    private List<Collection<String>> compareByCollection(Collection<String> currentCollections,
                                                         Collection<String> snapshotCollections,
                                                         Predicate<String> predicate) {
        //交集
        Collection<String> intersection = CollectionUtil.intersection(currentCollections, snapshotCollections);
        //add
        Collection<String> addRuleCodes = CollectionUtil.disjunction(currentCollections, intersection);
        //del
        Collection<String> delRuleCodes = CollectionUtil.disjunction(snapshotCollections, intersection);
        if (Objects.nonNull(predicate)) {
            intersection = intersection.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
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
    private void autoPublish() {

    }
}
