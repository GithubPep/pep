package com.example.pep.iot.business.rule.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.example.pep.iot.business.rule.context.RuleContext;
import com.example.pep.iot.business.rule.dto.PassPersonDto;
import com.example.pep.iot.business.rule.dto.PassRuleDto;
import com.example.pep.iot.business.rule.dto.PassRulePayload;
import com.example.pep.iot.business.rule.entity.PassRule;
import com.example.pep.iot.business.rule.entity.RulePersonSummary;
import com.example.pep.iot.utils.ResultUtils;
import com.example.pep.iot.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 下发参数生成
 *
 * @author LiuGang
 * @since 2022-03-08 11:52 AM
 */
@Slf4j
@Component
public class ParamHandler {

    /**
     * 组装下发参数
     * <p>
     * 增量数据 0 add 1 del 2 change
     *
     * @param ruleContext 上下文对象
     * @param payloadMap  各个device的集合
     */
    public void generateRegularParam(RuleContext ruleContext, Map<String, PassRulePayload> payloadMap) {
        String deviceCode = ruleContext.getDeviceCode();
        PassRulePayload passRulePayload;
        if (Objects.isNull(passRulePayload = payloadMap.get(deviceCode))) {
            passRulePayload = new PassRulePayload()
                    .setReqId(IdUtil.fastSimpleUUID())
                    .setDeviceCode(deviceCode);
            payloadMap.put(deviceCode, passRulePayload);
        }
        List<Collection<String>> personCompareRes = ruleContext.getPersonCompareRes();
        PassRule passRule = ruleContext.getPassRule();
        //处理 与快照相比,改变的规则 只需要关注人员变动
        if (ruleContext.getDeviceCompareRes().get(2).contains(deviceCode)) {
            log.info("在此规则{}中,当前设备:{}没有变动,只需要关注人员信息以及时间信息", passRule.getRuleName(), deviceCode);
            List<String> savePersons = new ArrayList<>(personCompareRes.get(0));
            List<String> delPersons = new ArrayList<>(personCompareRes.get(0));
            generatePersonAndTime(passRulePayload, ruleContext, savePersons, delPersons, false);
        }
        //处理 与快照相比,新增的规则 关注该规则中全部内容
        else if (ruleContext.getDeviceCompareRes().get(0).contains(deviceCode)) {
            log.info("在此规则{}中,当前设备:{}是新增的,将规则全部信息进行封装", passRule.getRuleName(), deviceCode);
            List<String> allSavePersons = new ArrayList<>(ruleContext.getCurrentPersonSummariesMap().keySet());
            generatePersonAndTime(passRulePayload, ruleContext, allSavePersons, null, false);
        }
        //处理 与快照相比,删除的规则 封装删除参数
        else {
            log.info("在此规则{}中,当前设备:{}是删除状态的,将规则id封装到del中", passRule.getRuleName(), deviceCode);
            generatePersonAndTime(passRulePayload, ruleContext, null, null, true);
        }
    }

    /**
     * @param passRulePayload payload对象
     * @param ruleContext     上下文
     * @param savePersons     该设备下,规则中需要新增的人,需要构造详细信息
     * @param delPersons      该设备下,规则中需要删除的人
     */
    private void generatePersonAndTime(PassRulePayload passRulePayload,
                                       RuleContext ruleContext,
                                       List<String> savePersons,
                                       List<String> delPersons,
                                       boolean flag) {
        PassRule passRule = ruleContext.getPassRule();
        //处理删除的设备
        if (flag) {
            List<String> delRules;
            if (CollectionUtil.isEmpty(delRules = passRulePayload.getDelRules())) {
                delRules = new ArrayList<>();
                passRulePayload.setDelRules(delRules);
            }
            delRules.add(ruleContext.getFlowNo());
        } else {
            List<PassRuleDto> saveRules;
            if (CollectionUtil.isEmpty(saveRules = passRulePayload.getSaveRules())) {
                saveRules = new ArrayList<>();
                passRulePayload.setSaveRules(saveRules);
            }
            PassRuleDto passRuleDto = new PassRuleDto()
                    .setRuleName(passRule.getRuleName())
                    .setRuleCode(ruleContext.getFlowNo())
                    .setRecognizeType(passRule.getRecognizeType())
                    .setTimeRules(ruleContext.getTimeRules())
                    .setTimestamp(
                            ruleContext.getPassRule().getUpdateTime() == null
                                    ?
                                    ruleContext.getPassRule().getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()
                                    :
                                    ruleContext.getPassRule().getUpdateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()
                    );
            //build person
            if (CollectionUtil.isNotEmpty(delPersons)) {
                passRuleDto.setDelPersons(delPersons);
            }
            Map<String, RulePersonSummary> personSummariesMap = ruleContext.getCurrentPersonSummariesMap();
            //获取需要保存person的详细信息.进行Dto对象的封装
            List<PassPersonDto> savePersonDtoList = StreamUtils.stream(savePersons).map(res -> {
                RulePersonSummary rulePersonSummary = personSummariesMap.get(res);
                ResultUtils.ifThrow(null == rulePersonSummary, "当前人员信息不存在");
                return BeanUtil.copyProperties(rulePersonSummary, PassPersonDto.class);
            }).collect(Collectors.toList());
            passRuleDto.setSavePersons(savePersonDtoList);
            saveRules.add(passRuleDto);
        }
    }
}
