package com.example.pep.iot.utils;

import com.example.pep.iot.exception.BizException;
import com.example.pep.iot.response.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Enumeration;

/**
 * 系统
 *
 * @author LiuGang
 * @since 2022-02-21 6:52 PM
 */
@Slf4j
public class SystemUtil {

    public static final String DEVICE_CODE;

    static {
        DEVICE_CODE = SystemUtil.getMac();
    }

    public static String getMac() {
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    // 排除回环地址、Docker网桥、不在线的网卡接口
                    if (item.isLoopback() || !item.isUp() || item.getName().contains("br-") || item.getName().contains("docker0")) {
                        continue;
                    }
                    if (address.getAddress() instanceof Inet4Address) {

                        Inet4Address inet4Address = (Inet4Address) address.getAddress();
                        log.info("****** {} {}", item.getName(), inet4Address.getHostAddress());

                        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inet4Address);
                        byte[] mac = networkInterface.getHardwareAddress();

                        if (mac == null) {
                            continue;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (byte b : mac) {
                            sb.append(String.format("%02X%s", b, ""));
                        }
                        return sb.toString();
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            log.error(e.getMessage(), e);
            throw new BizException(ResultEnum.SERVER_ERROR, "获取本机 IP 异常");
        }
    }


}
