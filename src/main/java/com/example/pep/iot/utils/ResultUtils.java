package com.example.pep.iot.utils;

import com.example.pep.iot.exception.BizException;

/**
 * @author LiuGang
 * @since 2022-03-07 11:37 AM
 */
public class ResultUtils {

    public static void ifThrow(Boolean flag, String msg) {
        if (flag) {
            throw new BizException(msg);
        }
    }
}
