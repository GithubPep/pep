package com.example.pep.iot.config.web;

import com.example.pep.iot.interceptor.SignatureInterceptor;
import com.example.pep.iot.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 *
 * @author LiuGang
 * @since 2022-03-04 2:40 PM
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignatureInterceptor());
        registry.addInterceptor(new TokenInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
