package com.example.pep.iot.emq.controller;

import com.example.pep.iot.emq.dto.AuthData;
import com.example.pep.iot.emq.service.DeviceRegisterService;
import com.example.pep.iot.emq.vo.DeviceRegisterResponse;
import com.example.pep.iot.response.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备认证注册到emqx控制器
 *
 * @author LiuGang
 * @since 2022-02-17 10:58 AM
 */
@Slf4j
@RestController
@RequestMapping("/link/device")
@RequiredArgsConstructor
public class DeviceRegisterController {

    private final DeviceRegisterService deviceRegisterService;

    @GetMapping("/register/{deviceCode}/{brandCode}")
    public ResultVO<DeviceRegisterResponse> register(@PathVariable String deviceCode, @PathVariable String brandCode) {
        DeviceRegisterResponse deviceRegisterResponse = deviceRegisterService.authRegister(deviceCode, brandCode);
        return ResultVO.ok(deviceRegisterResponse);
    }

    @GetMapping("/get-auth-clients")
    public ResultVO<List<String>> getAuthClients() {
        List<String> clients = deviceRegisterService.getAuthClients();
        return ResultVO.ok(clients);
    }

    @GetMapping("/del-auth-client/{deviceCode}")
    public ResultVO<Void> delAuthClient(@PathVariable String deviceCode) {
        deviceRegisterService.delAuthClient(deviceCode);
        return ResultVO.ok();
    }

    @GetMapping("/get-client-detail-by-deviceCode/{deviceCode}")
    public ResultVO<AuthData> getClientDetailByDeviceCode(@PathVariable String deviceCode) {
        AuthData authData = deviceRegisterService.getClientDetailByDeviceCode(deviceCode);
        return ResultVO.ok(authData);
    }
}
