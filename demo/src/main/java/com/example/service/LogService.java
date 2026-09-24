package com.example.service;

import com.example.pojo.EmpLog;
import com.example.pojo.PageResult;
import lombok.extern.java.Log;

public interface LogService{
    PageResult<EmpLog> pageSelect(Integer page, Integer pageSize);
}
