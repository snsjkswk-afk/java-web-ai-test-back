package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassQueryParam {
    String name;
    LocalDate begin;
    LocalDate end;
    Integer page;
    Integer pageSize;
}
