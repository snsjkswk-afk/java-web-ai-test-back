package com.example.controller;

import com.example.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionContrller {

    @ExceptionHandler
    public Result error(RuntimeException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result error(Exception e) {
        log.error("服务器发生异常: {}", e.getMessage());
        return Result.error("服务器发生异常");
    }

    @ExceptionHandler
    public Result error(DuplicateKeyException e) {
        log.info("数据库字段重复");
        int index = e.getMessage().lastIndexOf("for key '") + 9;
        String keyName = e.getMessage().substring(index);
        keyName = keyName.replace("'", "");
        return Result.error("字段重复: " + keyName);
    }
    @ExceptionHandler
    public Result error(Throwable e) {
        log.error("删除异常: {}", e.getMessage());
        return Result.error("对不起,该班级下有学生,不能直接删除");
    }
}
