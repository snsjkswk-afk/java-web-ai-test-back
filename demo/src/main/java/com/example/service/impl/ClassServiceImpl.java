package com.example.service.impl;

import com.example.Logoperation;
import com.example.mapper.ClassMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.*;
import com.example.service.ClassService;
import com.example.service.EmpLogService;
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
    public EmpLogService empLogService;

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

    @Override
    public void findAll() {
        classMapper.findAll();
    }

    @Logoperation
    @Override
    public void add(Clazz clazz) {
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("新增班级: " + clazz.getName());
        clazz.setBeginDate(LocalDate.now());
        clazz.setEndDate(LocalDate.now());
        classMapper.add(clazz);
        empLogService.insertLog(empLog);
    }

    @Override
    public Clazz getById(Integer id) {
        Clazz clazz = classMapper.getById(id);
        return clazz;
    }

    @Logoperation
    @Override
    public Clazz update(Clazz clazz) {
        Clazz clazz1 = classMapper.update(clazz);
        clazz1.setUpdateTime(LocalDateTime.now());
        return clazz1;
    }

    @Logoperation
    @Transactional
    @Override
    public Result delete(Integer id) {
        Clazz clazz = classMapper.getById(id);
        int count = studentMapper.countById( id);
        if (count > 0) {
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo("删除班级: " + clazz.getName());
            classMapper.delete(id);
            empLogService.insertLog(empLog);
        }else {
            return Result.error("该班级下有学生,不能直接删除");
        }
        return null;
    }
}
