package com.example.pep.iot.mqtt;

import java.sql.SQLException;

/**
 * mqtt订阅
 *
 * @author LiuGang
 * @since 2022-03-07 2:45 PM
 */
public interface MqttConsumer {

    String getTopic();

    void receiveMsg(String topic, String message);

}
