package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class recordTime {
    /*@Around("execution(* com.example.service.impl.ClassServiceImpl.getById(Integer))||" +
            "execution(* com.example.service.impl.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start)+ "ms");
        return result;
    }*/
    /*@Around("@annotation(com.example.Logoperation)")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start)+ "ms");
        return result;
    }*/
    @Before("execution(* com.example.controller.EmpController.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("开始执行,before");
        //1.获取目标对象
        Object target = joinPoint.getTarget();
        log.info("目标对象:{}", target);
        //2.获取方法名
        String methodName = joinPoint.getSignature().getName();
        log.info("方法名:{}", methodName);
        //3.获取参数
        Object[] args = joinPoint.getArgs();
        log.info("参数:{}", args);
        //4.获取类名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("类名:{}", className);
    }
}
