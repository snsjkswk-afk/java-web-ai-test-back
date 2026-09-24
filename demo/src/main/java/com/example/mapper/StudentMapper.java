package com.example.mapper;

import com.example.pojo.Student;
import com.example.pojo.StudentQueryParam;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    int countById(Integer id);
    Page<Student> ps(StudentQueryParam studentQueryParam);



    void deleteBach(List<Integer> ids);

    void add();

    Student getById(Integer id);

    void update(Student student);

    void disciplinaryHandling(Integer violation, Integer id, Short score);
}
