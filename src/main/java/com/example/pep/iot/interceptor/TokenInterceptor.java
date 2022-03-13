package com.example.pep.iot.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token校验
 *
 * @author LiuGang
 * @since 2022-03-09 6:19 PM
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String servletPath = request.getServletPath();
        if (StrUtil.isNotBlank(servletPath) && servletPath.startsWith("/t/")) {
            //todo valid token
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
