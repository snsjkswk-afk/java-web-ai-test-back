package com.example.controller;


import com.example.pojo.Emp;
import com.example.pojo.EmpLog;
import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/log")
public class LogController {
    @Autowired
    public LogService logService;

    @GetMapping("/page")
    public Result log(@RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<EmpLog> list = logService.pageSelect(page, pageSize);
        return Result.success(list);

    }


}
