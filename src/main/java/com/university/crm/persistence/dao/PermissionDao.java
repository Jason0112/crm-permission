package com.university.crm.persistence.dao;

import com.university.crm.bean.system.Permission;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * date: 2019/3/18
 * description :
 *
 * @author : zhencai.cheng
 */
@Component
public class PermissionDao {

    private final String statementNamespace = "Permission";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public PermissionDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public Permission findByPermissionSign(String permissionSign) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findByPermissionSign", permissionSign);
    }

    public void insertPermission(Permission permission) {
        sqlSessionTemplate.insert(statementNamespace + ".insert", permission);
    }

    public void updatePermission(Permission permission) {
        sqlSessionTemplate.update(statementNamespace + ".update", permission);
    }


    public void deletePermission(Integer permissionID) {
        sqlSessionTemplate.delete(statementNamespace + ".deletePermission", permissionID);
    }

    public Permission findByPermissionID(Integer permissionID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findByPermissionID", permissionID);
    }

    public int findPermissionByMenuIDCount(Integer menuID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findPermissionByMenuIDCount", menuID);
    }
}
