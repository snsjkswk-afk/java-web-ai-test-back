package com.example.service;

import com.example.pojo.Emp;
import com.example.pojo.EmpQueryParam;
import com.example.pojo.LoginInfo;
import com.example.pojo.PageResult;

public interface EmpService {
    /*PageResult<Emp> psIf(Integer start, Integer pageSize);*/

    PageResult<Emp> ps(EmpQueryParam EmpQueryParam);

    void save(Emp emp);

    Emp getById(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);
}
