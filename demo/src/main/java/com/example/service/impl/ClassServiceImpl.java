package com.example.service.impl;

import com.example.Logoperation;
import com.example.mapper.ClassMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.ClassQueryParam;
import com.example.pojo.Clazz;
import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.service.ClassService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    public ClassMapper classMapper;
    @Autowired
    public StudentMapper studentMapper;
    @Override
    public PageResult<Clazz> ps(ClassQueryParam classQueryParam) {
        PageHelper.startPage(classQueryParam.getPage(), classQueryParam.getPageSize());
        Page<Clazz> list = classMapper.ps(classQueryParam);
        return new PageResult<>(list.getTotal(),list.getResult());
    }

    @Logoperation
    @Override
    public void findAll() {
        classMapper.findAll();
    }

    @Logoperation
    @Override
    public void add(Clazz clazz) {
        clazz.setBeginDate(LocalDate.now());
        clazz.setEndDate(LocalDate.now());
        classMapper.add(clazz);
    }

    @Logoperation
    @Override
    public Clazz getById(Integer id) {
        Clazz clazz = classMapper.getById(id);
        return clazz;
    }

    @Override
    public Clazz update(Clazz clazz) {
        Clazz clazz1 = classMapper.update(clazz);
        clazz1.setUpdateTime(LocalDateTime.now());
        return clazz1;
    }
    @Transactional
    @Override
    public Result delete(Integer id) {
        int count = studentMapper.countById( id);
        if (count > 0) {
            classMapper.delete(id);
        }else {
            return Result.error("该班级下有学生,不能直接删除");
        }
        return null;
    }
}
