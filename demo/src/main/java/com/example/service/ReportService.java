package com.example.service;

import com.example.pojo.JobOption;

public interface ReportService {
    JobOption getEmpJobData();
    JobOption getGenderData();

    JobOption getStudentDegreeData();

    JobOption getStudentCountData();
}
