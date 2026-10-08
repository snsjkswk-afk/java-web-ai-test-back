package com.example.service.impl;

import com.example.mapper.EmpLogMapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import com.example.pojo.EmpLog;
import com.example.service.EmpLogService;
import com.example.utils.Jutis;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;
    @Autowired
    private EmpMapper empMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertLog(EmpLog empLog) {
        // 若调用方未显式设置操作人，则从当前登录令牌中解析
        empLog.setOperator(resolveCurrentOperatorName());
        empLogMapper.insert(empLog);
    }

    private String resolveCurrentOperatorName() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            if (!StringUtils.hasLength(token)) {
                return null;
            }
            Integer userId = Jutis.getUserIdFromToken(token);
            if (userId == null) {
                return null;
            }
            Emp emp = empMapper.getById(userId);
            return emp != null ? emp.getName() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
