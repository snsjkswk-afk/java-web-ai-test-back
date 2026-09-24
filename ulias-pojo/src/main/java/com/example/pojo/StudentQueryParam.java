package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQueryParam {
    String name;
    String degree;
    Integer  clazzId;
    Integer  page;
    Integer pageSize;
}
