package com.example.service;

import com.example.pojo.*;

import java.util.List;

public interface StudentService {

    PageResult<Student> ps(StudentQueryParam studentQueryParam);

    void deleteBach(List<Integer> ids);

    void add(Student student);

    Student getById(Integer id);

    void update(Student student);
    void disciplinaryHandling(Integer violation, Integer id, Short score);
}
