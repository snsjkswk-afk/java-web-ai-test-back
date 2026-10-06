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
    public Result deleteById(@PathVariable Integer id) {
        log.info("删除学生，ID: {}", id);
        studentService.deleteById(id);
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
    @PutMapping("/disciplinary/{id}")
    public Result disciplinaryHandling(@PathVariable Integer id,
                                       @RequestBody StudentViolationRecord record) {
        log.info("处理违纪信息，学生ID: {}, 类型: {}", id, record.getViolationType());
        studentService.disciplinaryHandling(id, record);
        return Result.success();
    }
    @PutMapping("/reduce/{id}/{count}/{score}")
    public Result reduceViolation(@PathVariable Integer id,
                                  @PathVariable Integer count,
                                  @PathVariable Integer score) {
        log.info("减少违纪，学生ID: {}, 分值: {}", id, score);
        if (count<0) {
            return Result.error("没有该违纪,无法删除");
        }
        studentService.reduceViolation(id, score);
        return Result.success();
    }
    @PutMapping("/revoke/{id}")
    public Result revokeViolation(@PathVariable Integer id) {
        log.info("撤销违纪，学生ID: {}", id);
        studentService.revokeViolation(id);
        return Result.success();
    }
    @GetMapping("/{id}/violations")
    public Result getViolationInfo(@PathVariable Integer id) {
        log.info("查看学生违纪信息，ID: {}", id);
        List<StudentViolationRecord> student = studentService.getViolationInfo(id);
        if (student == null) {
            return Result.error("学生不存在");
        }
        return Result.success(student);
    }
}
