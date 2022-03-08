package com.example.pep.iot.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.example.pep.iot.config.DeviceRegister;
import com.example.pep.iot.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

/**
 * @author LiuGang
 * @since 2022-03-07 2:36 PM
 */
@Slf4j
public class MqttCallback implements MqttCallbackExtended {

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info(">>>>>> onConnectCompleted, reconnect：{}", reconnect);

        IMqttActionListener mqttActionListener = new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                log.info(">>>>>> subscribe success：{}", JSONObject.toJSONString(DeviceRegister.mqttProperties.getTopic()));
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                log.error(">>>>>> subscribe failure：{}", JSONObject.toJSONString(DeviceRegister.mqttProperties.getTopic()));
            }
        };

        if ((MqttConnect.mqttClient != null) && (MqttConnect.mqttClient.isConnected())) {
            try {
                MqttConnect.mqttClient.subscribe(DeviceRegister.mqttProperties.getTopic(), DeviceRegister.mqttProperties.getQos(), null, mqttActionListener);
            } catch (MqttException e) {
                log.error("subscribe topic failed：" + e.getReasonCode(), e);
            }
        } else {
            log.error("subscribe topic failed, because mqttClient not connected.");
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("onConnectionLost, cause：{}", cause.toString());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        log.info(">>>>>> receive mqtt message topic is {}, message is {}", topic, payload.length() > 500 ? payload.substring(0, 500) : payload);
        MqttEntrance mqttEntrance = ApplicationUtils.getBean(MqttEntrance.class);
        mqttEntrance.receiveMqttMessage(topic, payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
