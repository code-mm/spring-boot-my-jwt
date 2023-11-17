package com.my.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;

@Configuration
public class MyAuthWebConfig implements WebMvcConfigurer {
    @Resource
    MyAuthHandlerInterceptor myAuthHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myAuthHandlerInterceptor);
    }
}
