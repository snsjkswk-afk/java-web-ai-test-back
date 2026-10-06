package com.example.service.impl;

import com.example.Logoperation;
import com.example.mapper.ClassMapper;
import com.example.mapper.StudentMapper;
import com.example.mapper.StudentViolationRecordMapper;
import com.example.pojo.*;
import com.example.service.EmpLogService;
import com.example.service.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    public StudentMapper studentMapper;
    @Autowired
    private StudentViolationRecordMapper violationRecordMapper;
    @Autowired
    private EmpLogService empLogService;
    @Override
    public PageResult<Student> ps(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        Page<Student> list = studentMapper.ps(studentQueryParam);

        return new PageResult<Student>(list.getTotal(),list.getResult());
    }

    @Logoperation
    @Transactional
    @Override
    public void deleteBach(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids)) {
           log.error("删除ID不能为空");
        }
        studentMapper.deleteBach(ids);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("批量删除学生, IDs: " + ids);
        empLogService.insertLog(empLog);
    }

    @Logoperation
    @Override
    public void deleteById(Integer id) {
        if (id == null)
            throw new RuntimeException("删除ID不能为空");
        studentMapper.deleteById(id);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("删除学生, ID: " + id);
        empLogService.insertLog(empLog);
    }

    @Logoperation
    @Override
    public void add(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.add(student);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("新增学生: " + student.getName());
        empLogService.insertLog(empLog);
    }

    @Override
    public Student getById(Integer id) {
        Student student = studentMapper.getById(id);
        return student;
    }

    @Logoperation
    @Transactional
    public void disciplinaryHandling(Integer id, StudentViolationRecord record) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在，ID: " + id);
        }
        record.setStudentId(id);
        violationRecordMapper.insert(record);
        studentMapper.disciplinaryHandling(1, id, record.getViolationScore().shortValue());
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("学生[" + student.getName() + "]违纪: " + record.getViolationType() + ", 扣" + record.getViolationScore() + "分");
        empLogService.insertLog(empLog);
    }

    @Logoperation
    @Transactional
    @Override
    public void reduceViolation(Integer id, Integer score) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在，ID: " + id);
        }
        // 检查是否有违纪记录
        List<StudentViolationRecord> records = violationRecordMapper.getViolationInfo(id);
        if (records.isEmpty()) {
            throw new RuntimeException("该学生没有违纪记录，无法减少");
        }
        // 检查是否有匹配分值的记录
        boolean hasMatchingScore = records.stream()
            .anyMatch(r -> r.getViolationScore() != null && r.getViolationScore().equals(score));
        if (!hasMatchingScore) {
            throw new RuntimeException("没有找到分值为 " + score + " 的违纪记录");
        }
        // 删除最早的、分值匹配的1条违纪记录
        violationRecordMapper.deleteLatest(id, score);
        // 重新计算剩余记录
        List<StudentViolationRecord> remaining = violationRecordMapper.getViolationInfo(id);
        int totalScore = remaining.stream().mapToInt(r -> r.getViolationScore() != null ? r.getViolationScore() : 0).sum();
        studentMapper.updateViolation(id, remaining.size(), (short) totalScore);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("学生[" + student.getName() + "]减少违纪, 扣分: " + score);
        empLogService.insertLog(empLog);
    }

    @Logoperation
    @Transactional
    @Override
    public void revokeViolation(Integer id) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在，ID: " + id);
        }
        // 删除所有违纪记录
        violationRecordMapper.deleteByStudentId(id);
        // 清零student表
        studentMapper.revokeViolation(id);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("学生[" + student.getName() + "]撤销违纪");
        empLogService.insertLog(empLog);
    }

    @Logoperation
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
        EmpLog empLog = new EmpLog();
        empLog.setOperateTime(LocalDateTime.now());
        empLog.setInfo("修改学生: " + student.getName());
        empLogService.insertLog(empLog);
    }

    @Override
    public List<StudentViolationRecord> getViolationInfo(Integer id) {
        List<StudentViolationRecord> student = violationRecordMapper.getViolationInfo(id);
        if (student == null) {
            throw new RuntimeException("学生不存在，ID: " + id);
        }
        return student;

    }
}
