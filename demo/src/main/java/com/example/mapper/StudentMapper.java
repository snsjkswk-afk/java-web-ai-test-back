package com.example.mapper;

import com.example.pojo.Student;
import com.example.pojo.StudentQueryParam;
import com.example.pojo.StudentViolationRecord;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

import java.util.List;

@Mapper
public interface StudentMapper {
    int countById(Integer id);
    Page<Student> ps(StudentQueryParam studentQueryParam);



    void deleteBach(List<Integer> ids);

    void deleteById(Integer id);

    void add(Student student);

    Student getById(Integer id);

    void update(Student student);

    void disciplinaryHandling(Integer violation, Integer id, Short score);

    void reduceViolation(Integer id, Integer violation, Short score);

    void revokeViolation(Integer id);

    void updateViolation(Integer id, Integer count, Short score);
}
