package com.example.pep.iot;

import lombok.Getter;

/**
 * 常量管理
 *
 * @author LiuGang
 * @since 2022-03-04 2:51 PM
 */
@Getter
public class Consistent {

    //更新时,设置备注,之后所有常量移到这里统一管理

    //header头信息
    @Getter
    public static class Headers {
        public static String SIGNATURE = "sign";
        public static String BRAND = "brand";
        public static String TIMESTAMP = "timestamp";
    }
}
