package com.example.pep.iot.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 响应VO对象
 *
 * @author LiuGang
 * @since 2022-02-17 11:00 AM
 */
public class ResultVO<T> {
    private static final long serialVersionUID = 8960474786737581150L;
    private Integer retCode;
    private String msg;
    private T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String msg) {
        this.retCode = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.retCode = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(ResultEnum resultEnum, T data) {
        this.retCode = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
        this.data = data;
    }

    public static <E> ResultVO<E> ok() {
        return ok(null);
    }

    public static <E> ResultVO<E> ok(E data) {
        return build(ResultEnum.SUCCESS, data);
    }

    public static <E> ResultVO<E> ok(Integer code, String message) {
        return code == null ? build(ResultEnum.SUCCESS.getCode(), message, null) : build(code, message, null);
    }

    public static <E> ResultVO<E> ok(Integer code, String message, E data) {
        return code == null ? build(ResultEnum.SUCCESS.getCode(), message, data) : build(code, message, data);
    }

    public static <E> ResultVO<E> err() {
        return build(ResultEnum.SERVER_ERROR, null);
    }

    public static <E> ResultVO<E> err(ResultEnum resultEnum) {
        return err(resultEnum.getCode(), resultEnum.getMessage());
    }

    public static <E> ResultVO<E> err(ResultEnum resultEnum, String message) {
        return err(resultEnum.getCode(), message);
    }

    public static <E> ResultVO<E> err(ResultEnum resultEnum, E data) {
        return build(resultEnum.getCode(), resultEnum.getMessage(), data);
    }

    public static <E> ResultVO<E> err(String message) {
        return err(ResultEnum.SERVER_ERROR.getCode(), message);
    }

    public static <E> ResultVO<E> err(Integer code, String message) {
        return code == null ? err(message) : build(code, message, null);
    }

    public static <E> ResultVO<E> err(Integer code, String message, E data) {
        return code == null ? err(message) : build(code, message, data);
    }

    public static <E> ResultVO<E> build(ResultEnum code) {
        return build(code, null);
    }

    private static <T> ResultVO<T> build(ResultEnum resultEnum, T data) {
        return build(resultEnum.getCode(), resultEnum.getMessage(), data);
    }

    private static <T> ResultVO<T> build(Integer code, String message, T data) {
        return new ResultVO(code, message, data);
    }

    @JsonIgnore
    public boolean isOk() {
        return ResultEnum.SUCCESS.getCode().equals(this.retCode);
    }

    public Integer getRetCode() {
        return this.retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultVO)) {
            return false;
        } else {
            ResultVO<?> other = (ResultVO) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$retCode = this.getRetCode();
                    Object other$retCode = other.getRetCode();
                    if (this$retCode == null) {
                        if (other$retCode == null) {
                            break label47;
                        }
                    } else if (this$retCode.equals(other$retCode)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    return other$data == null;
                } else return this$data.equals(other$data);
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResultVO;
    }

    public int hashCode() {
        Object $retCode = this.getRetCode();
        int result = 59 + ($retCode == null ? 43 : $retCode.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResultVO(retCode=" + this.getRetCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }
}
