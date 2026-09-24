package com.example.controller;

import com.example.pojo.Emp;
import com.example.pojo.EmpQueryParam;
import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    public EmpService empService;
    /*@GetMapping
    public Result pageSelect(@RequestParam(defaultValue = "1") Integer start,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询");
        PageResult<Emp> list = empService.psIf(start, pageSize);
        return Result.success(list);
    }*/
    @GetMapping
    public Result pageSelect(EmpQueryParam empQueryParam) {

        log.info("条件查询");
        PageResult<Emp> list = empService.ps(empQueryParam);
        return Result.success(list);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("保存员工信息");
        empService.save(emp);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {
        log.info("查询员工信息");
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("更新员工信息");
        empService.update(emp);
        return Result.success();
    }
}
