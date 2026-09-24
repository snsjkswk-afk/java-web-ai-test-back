package com.example.service.impl;

import com.example.mapper.EmpLogMapper;
import com.example.mapper.LogMapper;
import com.example.pojo.EmpLog;
import com.example.pojo.PageResult;
import com.example.service.LogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;
    @Override
    public PageResult<EmpLog> pageSelect(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<EmpLog> list = logMapper.pageSelect();
        return new PageResult<>(list.getTotal(), list.getResult());
    }
}
