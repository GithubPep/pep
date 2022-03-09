package com.example.pep.iot.business.rule.protocol;

import com.example.pep.iot.business.rule.dto.PassRulePayload;
import com.example.pep.iot.mqtt.MqttProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 通行记录下发
 *
 * @author LiuGang
 * @since 2022-03-08 11:20 AM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RuleDispatchService {

    private final MqttProducer mqttProducer;

    public void ruleDispatch(Map<String, PassRulePayload> payloadMap) {



    }
}
