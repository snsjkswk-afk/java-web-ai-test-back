package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentViolationRecord {
    private Integer id;
    private Integer studentId;
    private String studentName;
    private String studentNo;
    private String violationType;
    private Integer violationScore;
    private LocalDateTime violationTime;
    private String description;
}
