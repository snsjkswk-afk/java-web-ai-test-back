package com.example.controller;

import com.example.pojo.Dept;
import com.example.pojo.Result;
import com.example.service.DeptService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
@Slf4j
@RestController
public class DeptController {
    @Autowired
    public DeptService deptService;

    @GetMapping("depts")
    public Result findAll() {
        List<Dept> list = deptService.findAll();
        log.info("查询所有表的信息");
        return Result.success(list);
    }
    @DeleteMapping("depts/{id}")
    public Result delete(@PathVariable Integer id) {
        deptService.delete(id);
        log.debug("删除部门信息");
        return Result.success();
    }

    @PostMapping("depts")
    public Result add(@RequestBody Dept dept) {
        deptService.add(dept);
        log.info("添加部门信息");
        return Result.success();
    }

    @GetMapping("depts/{id}")
    public Result findById(@PathVariable Integer id) {
        Dept dept = deptService.findById(id);
        log.warn("查询部门信息");
        return Result.success(dept);
    }
    @PutMapping("depts")
    public Result update(@RequestBody Dept dept) {
        deptService.update(dept);
        log.error("更新部门信息");
        return Result.success();
    }
}