package com.example.mapper;

import com.example.pojo.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id")
    public Page<Emp> list(Integer start,Integer pageSize);

    /*@Select("select e.*,d.name depetName from emp e left join  dept d on e.dept_id = d.id " +
            "where e.name like concat('%',#{name},'%') and gender = #{gender} and entry_date " +
            "between #{entryDate} and #{creatDate}")*/

    public Page<Emp> list(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    public void save(Emp emp);

    Emp getById(Integer id);

    void update(Emp emp);

    @MapKey("name")
    List<Map<String, Object>> getJobData();

    @MapKey("gender")
    List<Map<String, Object>> getGenderData();

    @MapKey("degree")
    List<Map<String,Object>> getStudentDegreeData();

    @MapKey("clazz_id")
    List<Map<String,Object>> getStudentCountData();

    Emp login(Emp emp);
}
