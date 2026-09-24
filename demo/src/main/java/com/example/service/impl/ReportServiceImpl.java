package com.example.service.impl;

import com.example.mapper.EmpMapper;
import com.example.pojo.JobOption;
import com.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.getJobData();
        List jobList = list.stream().map(item -> item.get("job")).toList();
        List dataList = list.stream().map(item -> item.get("count")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public JobOption getGenderData() {
        List<Map<String, Object>> list = empMapper.getGenderData();
        List jobList = list.stream().map(item -> item.get("gender")).toList();
        List dataList = list.stream().map(item -> item.get("count")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public JobOption getStudentDegreeData() {
        List<Map<String, Object>> list = empMapper.getStudentDegreeData();
        List jobList = list.stream().map(item -> item.get("degree")).toList();
        List dataList = list.stream().map(item -> item.get("count")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public JobOption getStudentCountData() {
        List<Map<String,Object>> list = empMapper.getStudentCountData();
        List jobList = list.stream().map(item -> item.get("clazz")).toList();
        List dataList = list.stream().map(item -> item.get("count")).toList();
        return new JobOption(jobList, dataList);
    }
}





















