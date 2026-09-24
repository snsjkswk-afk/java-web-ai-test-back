package com.example.mapper;

import com.example.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Results ({
        @Result(column = "create_Time", property = "createTime"),
        @Result(column = "update_Time", property = "updateTime")
    })

    @Select("select * from dept")
    public List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    public void delete(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    public void add(Dept dept);

    @Select("select id, name, create_time createTime, update_time updateTime from dept where id = #{id}")
    public Dept findById(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    public void update(Dept dept);
}