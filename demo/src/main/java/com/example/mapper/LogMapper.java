package com.example.mapper;

import com.example.pojo.EmpLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    Page<EmpLog> pageSelect();
}
