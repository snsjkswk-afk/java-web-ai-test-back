package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpExpr {
    private Integer id;
    private Integer empId;
    private LocalDate begin;
    private LocalDate end;    //结束时间
    private String company;//公司名称
    private String job;//职位
}
