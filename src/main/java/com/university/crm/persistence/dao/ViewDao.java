package com.university.crm.persistence.dao;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.data.dto.search.AccountSearch;
import com.university.crm.data.dto.search.DepartmentSearch;
import com.university.crm.data.dto.search.MenuSearch;
import com.university.crm.data.dto.search.PermissionSearch;
import com.university.crm.data.dto.search.RoleSearch;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * date: 2019/4/1
 * description : 视图列表查询数据
 *
 * @author : zhencai.cheng
 */
@Component
public class ViewDao {

    private final String statementNamespace = "ViewData";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ViewDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    public List<JSONObject> findAccountTableList(AccountSearch search) {
        return sqlSessionTemplate.selectList(statementNamespace + ".findAccountTableList", search);
    }

    public List<JSONObject> findDepartmentTableList(DepartmentSearch search) {
        return sqlSessionTemplate.selectList(statementNamespace + ".findDepartmentTableList", search);
    }

    public List<JSONObject> findMenuTableList(MenuSearch search) {
        return sqlSessionTemplate.selectList(statementNamespace + ".findMenuTableList", search);
    }

    public List<JSONObject> findRoleTableList(RoleSearch search) {
        return sqlSessionTemplate.selectList(statementNamespace + ".findRoleTableList", search);
    }

    public List<JSONObject> findPermissionTableList(PermissionSearch search) {
        return sqlSessionTemplate.selectList(statementNamespace + ".findPermissionTableList", search);
    }


}
