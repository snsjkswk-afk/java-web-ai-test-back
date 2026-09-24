package com.example.service;

import com.example.pojo.EmpLog;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface EmpLogService {
    //记录新增员工日志
    public void insertLog(EmpLog empLog);
}