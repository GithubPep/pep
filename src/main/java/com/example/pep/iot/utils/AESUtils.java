package com.example.pep.iot.utils;

import cn.hutool.crypto.symmetric.AES;

/**
 * 加密
 *
 * @author LiuGang
 * @since 2022-03-22 22:02
 */
public class AESUtils {

    private final static String key = "pep-iot-12345678";

    //加密
    public static String encryptHex(String password, String salt) {
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，可以自定义
                key.getBytes(),
                // iv加盐，按照实际需求添加
                salt.getBytes());
        // 加密为16进制表示
        return aes.encryptHex(password);
    }

    //解密
    public static String decryptStr(String encryptHex, String salt) {
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，可以自定义
                key.getBytes(),
                // iv加盐，按照实际需求添加
                salt.getBytes());
        return aes.decryptStr(encryptHex);
    }
}
