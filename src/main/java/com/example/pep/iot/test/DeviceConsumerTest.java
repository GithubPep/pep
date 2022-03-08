package com.example.pep.iot.test;

import com.example.pep.iot.emq.consistent.TopicEnum;
import com.example.pep.iot.mqtt.MqttConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * test
 *
 * @author LiuGang
 * @since 2022-03-07 3:04 PM
 */
@Slf4j
@Component
public class DeviceConsumerTest implements MqttConsumer {
    @Override
    public String getTopic() {
        return TopicEnum.getTopicFuzzyMatching(TopicEnum.DEVICE_SUB_TOPIC);
    }

    @Override
    public void receiveMsg(String topic, String message) {
        log.info("monitor log notice topic is {}, message is {}", topic, message);
    }
}
