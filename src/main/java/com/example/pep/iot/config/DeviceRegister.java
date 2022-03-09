package com.example.pep.iot.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.pep.iot.emq.dto.DeviceMQTTConnection;
import com.example.pep.iot.emq.service.DeviceRegisterService;
import com.example.pep.iot.emq.vo.DeviceRegisterResponse;
import com.example.pep.iot.mqtt.MqttConnect;
import com.example.pep.iot.mqtt.MqttProperties;
import com.example.pep.iot.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 设备注册类,service充当一个设备,进行发布订阅topic
 *
 * @author LiuGang
 * @since 2022-02-16 5:59 PM
 */
@Slf4j
@Service
@Order
public class DeviceRegister implements InitializingBean {

    public static MqttProperties mqttProperties;

    @Resource
    private DeviceRegisterService deviceRegisterService;

    @Override
    public void afterPropertiesSet() {
        this.deviceRegister();
    }

    @Transactional
    public void deviceRegister() {
        DeviceRegisterResponse response = deviceRegisterService.authRegister(SystemUtil.DEVICE_CODE, "pep");

        mqttProperties = new MqttProperties();
        mqttProperties.setTopic(response.getDeviceMQTTTopics().getSubTopics().toArray(new String[0]));

        int[] qosArray = new int[mqttProperties.getTopic().length];
        Arrays.fill(qosArray, 1);

        mqttProperties.setQos(qosArray);
        DeviceMQTTConnection deviceMQTTConnection = response.getDeviceMQTTConnection();
        mqttProperties.setClientId(response.getClientId());
        mqttProperties.setUsername(deviceMQTTConnection.getUsername());
        mqttProperties.setPassword(deviceMQTTConnection.getPassword());
        mqttProperties.setHost("tcp://" + deviceMQTTConnection.getIp() + ":" + deviceMQTTConnection.getPort());
        MqttConnect.connect(mqttProperties);
        log.info("device:{}register res:\n{}", SystemUtil.DEVICE_CODE, JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
    }


}
