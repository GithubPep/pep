package com.example.pep.iot.response;

public enum ResultEnum {
    SUCCESS(0, "success"),
    SERVER_ERROR(1, "接口异常 common error"),
    PARAM_ERROR(11, "incorrect parameter"),
    FAILED(500, "服务发生未知异常,请联系系统管理员");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
