package com.university.crm.persistence.dao;

import com.university.crm.bean.system.Role;
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
public class RoleDao {

    private final String statementNamespace = "Role";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public RoleDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public Role findRoleName(Role role) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findRoleName", role);
    }

    public void insertRole(Role role) {
        sqlSessionTemplate.insert(statementNamespace + ".insertRole", role);
    }

    public void updateRole(Role role) {
        sqlSessionTemplate.update(statementNamespace + ".updateRole", role);
    }

    public void deleteRole(Integer roleID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteRole", roleID);
    }
}
