package com.example.pep.iot.response;

public enum ResultEnum {

    //前端根据code码,判断需不需要提示,0,需要提示
    SUCCESS(0, "操作成功"),
    //1,返回查询信息,不需要提示
    QUERY_SUCCESS(1, "查询成功"),

    PARAM_ERROR(-1, "参数异常"),
    SERVER_ERROR(-2, "业务异常"),
    SIGNATURE_ERROR(-3, "签名异常"),
    FAILED(-500, "服务发生未知异常,请联系系统管理员");

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
