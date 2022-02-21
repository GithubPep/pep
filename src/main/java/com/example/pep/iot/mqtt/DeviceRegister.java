package com.example.pep.iot.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * 设备注册类,service充当一个设备,进行发布订阅topic
 *
 * @author LiuGang
 * @since 2022-02-16 5:59 PM
 */
@Slf4j
@Service
public class DeviceRegister implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        this.deviceRegister();
    }

    private void deviceRegister() {

    }
}
