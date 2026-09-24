package com.example.aop;

import com.example.mapper.OperateLogMapper;
import com.example.pojo.OperateLog;
import com.example.utils.Jutis;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class OperateLogAop {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Around("@annotation(logoperation)")
    public Object recordLog(ProceedingJoinPoint joinPoint,Logoperation logoperation) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 当前时间
        long endTime = System.currentTimeMillis();
        // 耗时
        long costTime = endTime - startTime;
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId());
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(joinPoint.getTarget().getClass().getName());
        operateLog.setMethodName(joinPoint.getSignature().getName());
        operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        operateLog.setReturnValue(result.toString());
        operateLog.setCostTime(costTime);
        operateLogMapper.insert(operateLog);
        return result;
    }

    private Integer getCurrentUserId() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }

            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");

            if (token != null && !token.isEmpty()) {
                Integer userId = Jutis.getUserIdFromToken(token);
                return userId;
            }
        } catch (Exception e) {
            log.error("获取当前用户ID失败", e);
        }
        return null;
    }
}
