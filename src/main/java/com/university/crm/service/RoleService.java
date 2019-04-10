package com.university.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.system.Role;
import com.university.crm.data.dto.RoleBind;
import com.university.crm.data.dto.search.RoleSearch;
import com.university.crm.interceptor.mybatis.PageHelper;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.persistence.dao.AccountRolePermissionDao;
import com.university.crm.persistence.dao.RoleDao;
import com.university.crm.persistence.dao.ViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2019/3/18
 * description : 角色业务
 *
 * @author : zhencai.cheng
 */
@Service
public class RoleService {

    private RoleDao roleDao;

    private AccountRolePermissionDao accountRolePermissionDao;
    private ViewDao viewDao;

    @Autowired
    public RoleService(RoleDao roleDao, AccountRolePermissionDao accountRolePermissionDao, ViewDao viewDao) {
        this.roleDao = roleDao;
        this.accountRolePermissionDao = accountRolePermissionDao;
        this.viewDao = viewDao;
    }

    public void upsert(Role role) {
        Role db = roleDao.findRoleName(role);
        if (db == null) {
            roleDao.insertRole(role);
        } else {
            role.setRoleID(db.getRoleID());
            roleDao.updateRole(role);
        }
    }

    public PageInfo<JSONObject> findTableList(RoleSearch search, Page page) {
        PageHelper.startPage(page);
        List<JSONObject> dataList = viewDao.findRoleTableList(search);
        return new PageInfo<>(dataList);
    }

    public void delete(Integer roleID) {
        this.deleteRoleRelation(roleID);
        roleDao.deleteRole(roleID);
    }

    public void deleteRoleRelation(Integer roleID) {
        accountRolePermissionDao.deleteRolePermissionByRoleID(roleID);
        accountRolePermissionDao.deleteAccountRoleRelationByRoleID(roleID);
    }

    public void bindPermission(RoleBind bind) {
        accountRolePermissionDao.deleteRolePermissionByRoleID(bind.getRoleID());
        accountRolePermissionDao.insertRolePermissionRelation(bind);
    }

    public JSONObject findRolePermission(Integer roleID) {
        return accountRolePermissionDao.findRolePermission(roleID);
    }
}
