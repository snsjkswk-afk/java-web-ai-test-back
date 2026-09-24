package com.example.service;

import com.example.pojo.Dept;
import com.example.pojo.Result;

import java.util.List;

public interface DeptService {

    public List<Dept> findAll();

    public void delete(Integer id);

    public void add(Dept dept);

    public Dept findById(Integer id);

    public void update(Dept dept);
}
