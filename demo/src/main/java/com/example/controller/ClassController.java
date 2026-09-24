package com.example.controller;

import com.example.pojo.ClassQueryParam;
import com.example.pojo.Clazz;
import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/clazzs")
public class ClassController {
    @Autowired
    public ClassService classService;
    @GetMapping
    public Result pageSelect(ClassQueryParam classQueryParam) {
        log.info("条件查询");
        PageResult<Clazz> list = classService.ps(classQueryParam);
        return Result.success(list);
    }
    @GetMapping("list")
    public Result findAll() {
        log.info("查询所以班级成员");
        classService.findAll();
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody Clazz clazz) {
        log.info("添加班级信息");
        classService.add(clazz);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("查询班级信息");
        Clazz clazz = classService.getById(id);
        return Result.success(clazz);
    }
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("更新班级信息");
        Clazz clazz1 = classService.update(clazz);
        return Result.success(clazz1);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级信息");
        classService.delete(id);
        return Result.success();
    }

}
