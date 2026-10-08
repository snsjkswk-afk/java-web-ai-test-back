package com.example.service.impl;

import com.example.Logoperation;
import com.example.mapper.DeptMapper;
import com.example.pojo.Dept;
import com.example.pojo.Emp;
import com.example.pojo.EmpLog;
import com.example.service.DeptService;
import com.example.service.EmpLogService;
import com.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpLogService empLogService;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Logoperation
    @Override
    public void delete(Integer id) {
        Dept dept = deptMapper.findById(id);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("删除部门:"+dept.getName());
        empLogService.insertLog(empLog);
        deptMapper.delete(id);
    }

    @Logoperation
    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("添加部门:"+dept.getName());
        deptMapper.add(dept);
        empLogService.insertLog(empLog);
    }

    @Override
    public Dept findById(Integer id) {
        return deptMapper.findById(id);
    }

    @Logoperation
    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

}