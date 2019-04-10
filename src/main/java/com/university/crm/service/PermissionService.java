package com.university.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.system.Permission;
import com.university.crm.data.dto.search.PermissionSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageHelper;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.persistence.dao.AccountRolePermissionDao;
import com.university.crm.persistence.dao.PermissionDao;
import com.university.crm.persistence.dao.ViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * date: 2019/3/18
 * description : 权限业务
 *
 * @author : zhencai.cheng
 */
@Service
public class PermissionService {

    private PermissionDao permissionDao;
    private AccountRolePermissionDao accountRolePermissionDao;
    private ViewDao viewDao;

    @Autowired
    public PermissionService(PermissionDao permissionDao,
                             AccountRolePermissionDao accountRolePermissionDao,
                             ViewDao viewDao) {
        this.permissionDao = permissionDao;
        this.accountRolePermissionDao = accountRolePermissionDao;
        this.viewDao = viewDao;
    }

    public void upsert(Permission permission) throws BusinessException {
        Permission db = permissionDao.findByPermissionSign(permission.getPermissionSign());
        if (permission.getPermissionID() == null) {
            if (db != null) {
                throw new BusinessException(db.getPermissionSign() + "已存在", true);
            }
            permissionDao.insertPermission(permission);
        } else {
            if (Objects.equals(db.getPermissionID(), permission.getPermissionID())) {
                throw new BusinessException(db.getPermissionSign() + "已存在", true);
            }
            permissionDao.updatePermission(permission);
        }
    }

    public PageInfo<JSONObject> findTableList(PermissionSearch search, Page page) {
        PageHelper.startPage(page);
        List<JSONObject> list = viewDao.findPermissionTableList(search);
        return new PageInfo<>(list);
    }

    public void delete(Integer permissionID) {
        permissionDao.deletePermission(permissionID);
        accountRolePermissionDao.deleteRolePermissionByPermissionID(permissionID);
    }

    public Permission findPermission(Integer permissionID) {
        return permissionDao.findByPermissionID(permissionID);
    }

    public int findPermissionByMenuIDCount(Integer menuID) {
        return permissionDao.findPermissionByMenuIDCount(menuID);
    }
}
