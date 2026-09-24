package com.example.service;

import com.example.pojo.ClassQueryParam;
import com.example.pojo.Clazz;
import com.example.pojo.PageResult;
import com.example.pojo.Result;

public interface ClassService {
    PageResult<Clazz> ps(ClassQueryParam classQueryParam);

    void findAll();

    void add(Clazz clazz);

    Clazz getById(Integer id);

    Clazz update(Clazz clazz);

    Result delete(Integer id);
}
