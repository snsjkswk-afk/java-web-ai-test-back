package com.example.service;

import com.example.pojo.*;

import java.util.List;

public interface StudentService {

    PageResult<Student> ps(StudentQueryParam studentQueryParam);

    void deleteBach(List<Integer> ids);

    void deleteById(Integer id);

    void add(Student student);

    Student getById(Integer id);

    void update(Student student);

    void disciplinaryHandling(Integer id, StudentViolationRecord record);

    void reduceViolation(Integer id, Integer score);

    void revokeViolation(Integer id);

    List<StudentViolationRecord> getViolationInfo(Integer id);
}
