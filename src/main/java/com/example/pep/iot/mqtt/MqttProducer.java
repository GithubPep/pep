package com.example.pep.iot.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * mqtt消息发送
 *
 * @author LiuGang
 * @since 2022-03-08 11:22 AM
 */
@Slf4j
@Component
public class MqttProducer {

    public void sendMsg(String topic, String payload) {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);

        if (MqttConnect.mqttClient != null && MqttConnect.mqttClient.isConnected()) {
            try {
                log.info(">>>>>> mqtt publisher topic is {}, message is {}", topic, payload);
                MqttConnect.mqttClient.publish(topic, message);
            } catch (MqttException e) {
                log.error("publish topic: " + topic + " failed", e);
            }
        } else {
            log.error(String.format("publish topic: %s failed, mqttClient not connected", topic));
        }

    }
}
