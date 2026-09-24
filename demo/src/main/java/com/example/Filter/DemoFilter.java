package com.example.Filter;

import com.example.utils.Jutis;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url  = request.getRequestURL().toString();

        if(url.contains("login")) {
            filterChain.doFilter(request,response);
        }
        String token = request.getHeader("token");

        if(!StringUtils.hasLength(token)) {
            log.info("获取到jwt令牌为空,返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }

        try {
            Jutis.ParseJwt(token);
        }catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败, 返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }
       filterChain.doFilter(request,response);
    }
}
