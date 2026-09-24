package com.example.controller;

import com.example.pojo.*;
import com.example.service.impl.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    public StudentServiceImpl studentService;
    @GetMapping
    public Result PageSelect(StudentQueryParam studentQueryParam) {
        log.info("条件查询");
        PageResult<Student> list = studentService.ps(studentQueryParam);
        return Result.success(list);
    }
    @DeleteMapping("/{ids}")
    public Result deleteBach(@PathVariable String ids) {
        log.info("批量删除学生，IDs: {}", ids);
        if (ids == null || ids.trim().isEmpty()) {
            return Result.error("删除ID不能为空");
        }
        List<Integer> result = Arrays.stream(ids.split( ","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        studentService.deleteBach(result);
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("添加学生信息");
        studentService.add(student);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查询学生");
        Student student = studentService.getById(id);
        if (student == null) {
            return Result.error("学生不存在");
        }
        return Result.success(student);
    }
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学生信息");
        studentService.update(student);
        return Result.success();
    }
    @PutMapping("/{violation}/{id}/{score}")
    public Result disciplinaryHandling(@PathVariable Integer violation,
                                       @PathVariable Integer id,
                                       @PathVariable Short score) {
        log.info("处理违纪信息");
        studentService.disciplinaryHandling(violation,id,score);
        return Result.success();
    }
}
