package com.example.service.impl;

import com.example.Logoperation;
import com.example.mapper.EmpExprMapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.*;
import com.example.service.EmpLogService;
import com.example.service.EmpService;
import com.example.utils.Jutis;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    public EmpMapper empMapper;
    @Autowired
    public EmpExprMapper empExprMapper;

    @Autowired
    public EmpLogService empLogService;
    @Autowired
    public Jutis jwtUtils;
    @Override
    public PageResult<Emp> ps(EmpQueryParam empQueryParam) {
        log.info("条件查询");
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        Page<Emp> empList = empMapper.list(empQueryParam);
        //封装结果
        return new PageResult<>(empList.getTotal(), empList.getResult());
    }

    @Logoperation
    @Transactional
    @Override
    public void save(Emp emp) {
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setEntryDate(LocalDate.now());
            empMapper.save(emp);
            //批量添加员工经历
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(expr -> {
                    expr.setEmpId(emp.getId());
                });
            }
            empExprMapper.insertBatch(exprList);
        }finally {
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo("新增员工: " + emp.getName());
            empLogService.insertLog(empLog);
        }
    }
    @Logoperation
    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        return emp;
    }
    @Logoperation
    @Transactional(rollbackFor = Exception.class )
    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        //更新经历
        empMapper.update(emp);
        //先删除原来员工的经历
        empExprMapper.deleteByEmpId(Arrays.asList(emp.getId()));
        //再批量添加员工经历
        Integer id = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(expr -> {expr.setEmpId( id);});
            empExprMapper.insertBatch(exprList);
        }
    }


    @Override
    public LoginInfo login(Emp emp) {

        Emp login = empMapper.login(emp);
        if(login== null) {
            return null;
        }
        Integer id = login.getId();
        String usename = login.getUsername();
        String token = Jutis.GnerateJwt(id, usename);
        LoginInfo loginInfo = new LoginInfo(login.getId(),
                login.getUsername(),login.getName(),token);
        return loginInfo;
    }



   /* public PageResult<Emp> psIf(Integer start, Integer pageSize) {
        log.info("分页查询");
        PageHelper.startPage(start, pageSize);
        Page<Emp> empList = empMapper.list(start, pageSize);
        //封装结果
        return new PageResult<>(empList.getTotal(), empList.getResult());
    }*/

}
