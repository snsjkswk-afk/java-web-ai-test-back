package com.example.intercepter;

import com.example.utils.Jutis;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
//@Component
public class DemoIntercept implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截前的配置");
        String url = request.getRequestURL().toString();
        if(url.contains("login")) {
            return true;
        }
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            log.info("获取到jwt令牌为空,返回错误结果");
            return false;
        }

        try {
            Jutis.ParseJwt(token);
        }catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("操作");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("拦截后的配置");
    }
}


