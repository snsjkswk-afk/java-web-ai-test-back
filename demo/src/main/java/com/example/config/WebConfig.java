package com.example.config;

import com.example.intercepter.DemoIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private DemoIntercept demoIntercept;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoIntercept).addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }
}
