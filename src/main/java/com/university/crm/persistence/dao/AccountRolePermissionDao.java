package com.university.crm.persistence.dao;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.data.dto.AccountBind;
import com.university.crm.data.dto.RoleBind;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * date: 2019/3/27
 * description :
 *
 * @author : zhencai.cheng
 */
@Component
public class AccountRolePermissionDao {

    private final String statementNamespace = "AccountRolePermission";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AccountRolePermissionDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    public boolean checkPermission(Map<String, String> queryMap) {
        Integer count = sqlSessionTemplate.selectOne(statementNamespace + ".checkPermission", queryMap);
        return count != null && !Objects.equals(count, 0);
    }

    public void deleteAccountRoleRelationByAccountID(Integer accountID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteAccountRoleRelationByAccountID", accountID);
    }

    public void insertAccountRoleRelation(AccountBind bind) {
        sqlSessionTemplate.insert(statementNamespace + ".insertAccountRoleRelation", bind);
    }

    public void deleteAccountRoleRelationByRoleID(Integer roleID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteAccountRoleRelationByRoleID", roleID);
    }

    public void deleteRolePermissionByRoleID(Integer roleID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteRolePermissionByRoleID", roleID);
    }

    public void insertRolePermissionRelation(RoleBind bind) {
        sqlSessionTemplate.insert(statementNamespace + ".insertRolePermissionRelation", bind);
    }

    public JSONObject findRolePermission(Integer roleID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findRolePermission", roleID);
    }

    public JSONObject findAccountRole(Integer accountID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findAccountRole", accountID);
    }

    public void deleteRolePermissionByPermissionID(Integer permissionID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteRolePermissionByPermissionID", permissionID);
    }

    public List<JSONObject> findMenuList(String account) {
        return sqlSessionTemplate.selectList(statementNamespace+".findMenuList",account);
    }
}
