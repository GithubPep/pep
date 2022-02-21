package com.example.pep.iot.exception;

import com.example.pep.iot.response.ResultEnum;

/**
 * 业务异常类
 *
 * @author LiuGang
 * @since 2022-02-17 11:06 AM
 */
public class BizException extends RuntimeException {

    private final Integer code;
    private final String message;

    public BizException(String message) {
        super(message);
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = message;
    }


    public BizException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.message = resultEnum.getMessage();
        this.code = resultEnum.getCode();
    }

    public BizException(ResultEnum resultEnum, String message) {
        super(message);
        this.message = message;
        this.code = resultEnum.getCode();
    }

    public BizException(Integer retCode, String message) {
        super(message);
        this.message = message;
        this.code = retCode;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
