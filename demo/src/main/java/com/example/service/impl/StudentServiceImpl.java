package com.example.service.impl;

import com.example.Logoperation;
import com.example.mapper.ClassMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.*;
import com.example.service.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    public StudentMapper studentMapper;
    @Override
    public PageResult<Student> ps(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        Page<Student> list = studentMapper.ps(studentQueryParam);

        return new PageResult<Student>(list.getTotal(),list.getResult());
    }

    @Transactional
    @Override
    public void deleteBach(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids)) {
           log.error("删除ID不能为空");
        }
        studentMapper.deleteBach(ids);
    }

    @Logoperation
    @Override
    public void add(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.add();
    }

    @Override
    public Student getById(Integer id) {
        Student student = studentMapper.getById(id);
        return student;
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);

    }

    @Transactional
    public void disciplinaryHandling(Integer violation, Integer id, Short score) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在，ID: " + id);
        }
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.disciplinaryHandling(violation, id, score);
    }
}
