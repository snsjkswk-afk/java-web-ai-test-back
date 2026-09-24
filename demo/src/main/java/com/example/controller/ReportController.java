package com.example.controller;


import com.example.pojo.JobOption;
import com.example.pojo.Result;
import com.example.service.impl.ReportServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    public ReportServiceImpl reportService;
    @GetMapping("/empsJobData")
    public Result getEmpJobData() {
        log.info("查询员工数据");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    @GetMapping("/empsGenderData")
    public Result getGenderData() {
        log.info("查询员工性别数据");
        JobOption jobOption = reportService.getGenderData();
        return Result.success(jobOption);
    }
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData() {
        log.info("查询学生学历数据");
        JobOption jobOption = reportService.getStudentDegreeData();
        return Result.success(jobOption);
    }
    @GetMapping("/studentCountData")
    public Result getStudentCountData() {
        log.info("查询每个班级数量的数据");
        JobOption list = reportService.getStudentCountData();
        return Result.success(list);
    }
}
