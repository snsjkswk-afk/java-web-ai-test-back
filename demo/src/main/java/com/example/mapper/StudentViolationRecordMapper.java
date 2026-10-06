package com.example.mapper;

import com.example.pojo.StudentViolationRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentViolationRecordMapper {
    void insert(StudentViolationRecord record);

    List<StudentViolationRecord> getViolationInfo(Integer id);

    void deleteLatest(Integer id, Integer score);

    void deleteByStudentId(Integer id);
}
