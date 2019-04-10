package com.university.crm.persistence.dao;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.system.Department;
import com.university.crm.data.dto.search.DepartmentSearch;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * date: 2019/3/27
 * description :
 *
 * @author : zhencai.cheng
 */
@Component
public class DepartmentDao {

    private final String statementNamespace = "Department";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public DepartmentDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    public JSONObject findDepartmentByID(Integer departmentID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findDepartmentByID", departmentID);
    }

    public Department findDepartmentBySign(String departmentSign) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findDepartmentBySign", departmentSign);
    }

    public void insertDepartment(Department department) {
        sqlSessionTemplate.insert(statementNamespace + ".insertDepartment", department);
    }

    public void updateDepartment(Department department) {
        sqlSessionTemplate.update(statementNamespace + ".updateDepartment", department);
    }

    public void deleteDepartment(Integer departmentID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteDepartment", departmentID);
    }
}
