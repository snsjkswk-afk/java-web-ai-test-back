package com.example.mapper;

import com.example.pojo.ClassQueryParam;
import com.example.pojo.Clazz;
import com.example.pojo.PageResult;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassMapper {

    Page<Clazz> ps(ClassQueryParam classQueryParam);

    void findAll();

    void add(Clazz clazz);

    Clazz getById(Integer id);

    Clazz update(Clazz clazz);


    void delete(Integer id);
}
