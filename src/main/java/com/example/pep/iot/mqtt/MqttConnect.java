package com.example.pep.iot.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author LiuGang
 * @since 2022-03-07 2:35 PM
 */
@Slf4j
public class MqttConnect {

    public static MqttAsyncClient mqttClient = null;

    public static void connect(MqttProperties mqttProperties) {

        if (mqttClient == null) {
            try {
                MemoryPersistence persistence = new MemoryPersistence();
                mqttClient = new MqttAsyncClient(mqttProperties.getHost(), mqttProperties.getClientId(), persistence);
                // callback
                mqttClient.setCallback(new MqttCallback());
            } catch (Exception e) {
                log.error("new MqttClient failed " + e);
                return;
            }
        }

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(mqttProperties.getUsername());
        connectOptions.setPassword(mqttProperties.getPassword().toCharArray());
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        connectOptions.setCleanSession(true);

        try {
            log.info("... starting connect mqtt");
            mqttClient.connect(connectOptions);
        } catch (MqttException e) {
            log.error("mqttClient connect failed: " + e.getReasonCode(), e);
        }
    }
}
