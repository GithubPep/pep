package com.example.pep.iot.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * 获取异常输出到控制台的日志
 *
 * @author LiuGang
 * @since 2022-02-17 11:08 AM
 */
public class ErrorUtil {

    private static final Method METHOD = getPrintMethod();


    /**
     * 获取异常栈
     *
     * @param e 异常
     * @return 异常栈信息
     */
    public static String getExceptionStackPrint(Throwable e) {
        if (e == null || METHOD == null) {
            return "";
        }

        StringWriter stringWriter = new StringWriter();
        try {
            METHOD.invoke(e, new PrintWriter(stringWriter));
        } catch (Exception invokeException) {
            //log.error("exception print failed", invokeException);
        }
        return stringWriter.toString();
    }

    /**
     * 获取异常打印类
     *
     * @return 异常打印类
     */
    private static Method getPrintMethod() {
        try {
            return Throwable.class.getMethod("print" + "Stack" + "Trace", PrintWriter.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
