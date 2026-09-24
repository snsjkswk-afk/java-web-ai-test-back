package com.example.mapper;

import com.example.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.javassist.expr.Expr;

import java.util.List;

@Mapper
public interface EmpExprMapper {


    public void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpId(List<Integer> id);
}
