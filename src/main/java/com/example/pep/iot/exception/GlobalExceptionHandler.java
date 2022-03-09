package com.example.pep.iot.exception;

import com.example.pep.iot.response.ResultEnum;
import com.example.pep.iot.response.ResultVO;
import com.example.pep.iot.utils.ErrorUtil;
import com.example.pep.iot.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author LiuGang
 * @since 2022-02-17 11:07 AM
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.application.name:}")
    private String name;

    /**
     * 处理自定义的业务异常
     *
     * @param req 请求
     * @param e   异常
     * @return 响应
     */
    @ExceptionHandler(value = {BizException.class})
    public ResultVO<String> bizExceptionHandler(HttpServletRequest req, BizException e) {
        String errorData = String.format("%s模块发生业务异常,接口名:%s,异常原因:%s", name, req.getContextPath() + req.getServletPath(), ErrorUtil.getExceptionStackPrint(e));
        log.error("{}", errorData);
        return ResultVO.err(e.getCode(), e.getMessage(), errorData);
    }

    /**
     * 处理参数校验异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResultVO<String> validExceptionHandler(Exception e) {
        String message = "";
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            message = StreamUtils.stream(exception.getBindingResult().getFieldErrors())
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(","));

        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            message = StreamUtils.stream(exception.getConstraintViolations())
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
        }
        return new ResultVO<>(ResultEnum.PARAM_ERROR.getCode(), message, null);

    }

    /**
     * 处理其他异常
     *
     * @param req 请求
     * @param e   异常
     * @return 响应
     */
    @ExceptionHandler(value = Exception.class)
    public ResultVO<String> exceptionHandler(HttpServletRequest req, Exception e) {
        Throwable ex = e.getCause();
        while (ex != null) {
            if (ex instanceof BizException) {
                return bizExceptionHandler(req, (BizException) ex);
            } else {
                ex = ex.getCause();
            }
        }
        log.error("未知异常！原因是:{}", e.getMessage(), e);
        String errorData = String.format("%s模块发生未知异常,接口名:%s,异常原因:%s", name, req.getContextPath() + req.getServletPath(), ErrorUtil.getExceptionStackPrint(e));
        return ResultVO.err(ResultEnum.FAILED.getCode(), ResultEnum.FAILED.getMessage(), errorData);
    }
}
