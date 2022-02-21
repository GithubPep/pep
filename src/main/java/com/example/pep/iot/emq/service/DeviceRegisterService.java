package com.example.pep.iot.emq.service;

import cn.hutool.core.lang.id.NanoId;
import com.example.pep.iot.emq.config.EmqAuthConfig;
import com.example.pep.iot.emq.config.EmqMqttConfig;
import com.example.pep.iot.emq.consistent.TopicEnum;
import com.example.pep.iot.emq.dto.AuthData;
import com.example.pep.iot.emq.dto.DeviceMQTTConnection;
import com.example.pep.iot.emq.dto.DeviceMQTTTopics;
import com.example.pep.iot.emq.entity.EmqClientAuth;
import com.example.pep.iot.emq.mapper.EmqClientAuthMapper;
import com.example.pep.iot.emq.vo.DeviceRegisterResponse;
import com.example.pep.iot.redis.RedisConsistent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 设备注册
 *
 * @author LiuGang
 * @since 2022-02-17 11:13 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceRegisterService {

    private final EmqService emqService;

    private final EmqMqttConfig emqMqttConfig;

    private final EmqClientAuthMapper emqClientAuthMapper;

    private final EmqAuthConfig emqAuthConfig;

    private final ExpiringMap<String, AuthData> authDataCache = ExpiringMap.builder()
            .maxSize(128)
            .expiration(5 * 60, TimeUnit.SECONDS)
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .variableExpiration()
            .build();

    /**
     * 设备注册到emq
     *
     * @param deviceCode 设备id
     * @param brandCode  品牌id
     * @return 认证结果
     */
    @Transactional(rollbackFor = Exception.class)
    public DeviceRegisterResponse authRegister(String deviceCode, String brandCode) {
        AuthData authData = this.requestEmxRegister(deviceCode);
        // translate MQTT register result
        DeviceRegisterResponse response = new DeviceRegisterResponse();
        String clientId = authData.getClientId();
        response.setClientId(authData.getClientId());

        // MQTT connection information
        DeviceMQTTConnection connection = new DeviceMQTTConnection();
        connection.setIp(authData.getServiceAddress());
        connection.setPort(Integer.parseInt(authData.getServicePort()));
        connection.setKeepAlive(emqAuthConfig.getKeepAlive());
        connection.setUsername(authData.getUsername());
        connection.setPassword(authData.getPassword());
        response.setDeviceMQTTConnection(connection);

        // MQTT topics information TODO 为每个品牌构建appKey,目前使用brand
        DeviceMQTTTopics deviceMQTTTopics = new DeviceMQTTTopics();
        List<String> pubTopics = buildPubTopics(brandCode, clientId);
        List<String> subTopics = buildSubTopics(brandCode, clientId);
        deviceMQTTTopics
                .setPubTopics(pubTopics)
                .setSubTopics(subTopics);
        response.setDeviceMQTTTopics(deviceMQTTTopics);
        return response;
    }


    @Transactional(rollbackFor = Exception.class)
    public AuthData requestEmxRegister(String deviceCode) {
        //first get cache
        String cacheKey = String.format(RedisConsistent.EMQ_AUTH_CACHE, deviceCode);
        AuthData authData = authDataCache.get(cacheKey);
        if (Objects.isNull(authData)) {
            // from db get
            EmqClientAuth clientAuth = emqClientAuthMapper.selectOneByDeviceCode(deviceCode);
            if (Objects.isNull(clientAuth)) {
                //create clientId
                String clientId = "pep_" + deviceCode + "_" + NanoId.randomNanoId(6);
                String password = NanoId.randomNanoId(20);
                authData = new AuthData()
                        .setServiceAddress(emqMqttConfig.getMqttUrl())
                        .setServicePort(emqMqttConfig.getPort())
                        .setClientId(clientId)
                        .setPassword(password)
                        .setUsername(deviceCode);
                clientAuth = new EmqClientAuth();
                BeanUtils.copyProperties(authData, clientAuth);
                clientAuth.setDeviceCode(deviceCode);
                emqClientAuthMapper.insert(clientAuth);
                Integer code = emqService.addClient(clientId, password);
                if (code == 0) {
                    // save to db
                    BeanUtils.copyProperties(authData, clientAuth);
                    authDataCache.put(cacheKey, authData);
                }
            } else {
                authData = new AuthData();
                BeanUtils.copyProperties(clientAuth, authData);
            }
        }
        return authData;
    }

    /**
     * 构建pub topic
     */
    private List<String> buildPubTopics(String brandCode, String clientId) {
        List<String> pubTopics = new ArrayList<>();
        pubTopics.add(TopicEnum.getTopicExact(TopicEnum.DEVICE_PUB_TOPIC, brandCode, clientId));
        return pubTopics;
    }

    /**
     * 构建sub topic
     */
    private List<String> buildSubTopics(String brandCode, String clientId) {
        List<String> subTopics = new ArrayList<>();
        subTopics.add(TopicEnum.getTopicExact(TopicEnum.DEVICE_SUB_TOPIC, brandCode, clientId));
        return subTopics;
    }

    /**
     * 从emq http获取
     *
     * @return clients
     */
    public List<String> getAuthClients() {
        return emqService.getClients();
    }

    public AuthData getClientDetailByDeviceCode(String deviceCode) {
        //first get cache
        String cacheKey = String.format(RedisConsistent.EMQ_AUTH_CACHE, deviceCode);
        AuthData authData = authDataCache.get(cacheKey);
        if (Objects.isNull(authData)) {
            // from db get
            EmqClientAuth clientAuth = emqClientAuthMapper.selectOneByDeviceCode(deviceCode);
            if (Objects.isNull(clientAuth)) {
                return null;
            }
            authData = new AuthData();
            BeanUtils.copyProperties(clientAuth, authData);
            authDataCache.put(cacheKey, authData);
        }
        return authData;
    }

    //del emq client
    public void delAuthClient(String deviceCode) {

    }
}
