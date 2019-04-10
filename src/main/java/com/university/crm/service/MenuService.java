package com.university.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.system.Menu;
import com.university.crm.context.RequestThreadContext;
import com.university.crm.data.dto.search.MenuSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageHelper;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.persistence.dao.AccountRolePermissionDao;
import com.university.crm.persistence.dao.MenuDao;
import com.university.crm.persistence.dao.ViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2019/3/18
 * description : 菜单业务
 *
 * @author : zhencai.cheng
 */
@Service
public class MenuService {

    private MenuDao menuDao;
    private PermissionService permissionService;
    private AccountRolePermissionDao accountRolePermissionDao;
    private ViewDao viewDao;

    @Autowired
    public MenuService(MenuDao menuDao,
                       PermissionService permissionService,
                       AccountRolePermissionDao accountRolePermissionDao,
                       ViewDao viewDao) {
        this.menuDao = menuDao;
        this.permissionService = permissionService;
        this.accountRolePermissionDao = accountRolePermissionDao;
        this.viewDao = viewDao;
    }

    public void upsert(Menu menu) {
        if (menu.getMenuID() == null) {
            menuDao.insertMenu(menu);
        } else {
            menuDao.updateMenu(menu);
        }
    }

    public PageInfo<JSONObject> findTableList(MenuSearch search, Page page) {
        PageHelper.startPage(page);
        List<JSONObject> list = viewDao.findMenuTableList(search);
        return new PageInfo<>(list);
    }

    public void delete(Integer menuID) throws BusinessException {
        int total = permissionService.findPermissionByMenuIDCount(menuID);
        if (total > 0) {
            throw new BusinessException("当前菜单有子菜单不能删除", true);
        }
        menuDao.deleteMenu(menuID);
    }

    public Menu findMenu(Integer menuID) {
        return menuDao.findMenuByID(menuID);
    }


    public List<JSONObject> findMenuList() {
        return accountRolePermissionDao.findMenuList(RequestThreadContext.getAccount());
    }
}
