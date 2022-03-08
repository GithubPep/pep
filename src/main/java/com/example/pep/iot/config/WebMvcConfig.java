package com.example.pep.iot.config;

import org.springframework.context.annotation.Configuration;
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
        //  registry.addInterceptor(new SignatureInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
