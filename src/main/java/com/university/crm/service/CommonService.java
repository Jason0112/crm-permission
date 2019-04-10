package com.university.crm.service;

import com.university.crm.bean.system.Account;
import com.university.crm.context.RequestThreadContext;
import com.university.crm.exception.BusinessException;
import com.university.crm.persistence.dao.AccountDao;
import com.university.crm.persistence.dao.AccountRolePermissionDao;
import com.university.crm.util.MapKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * date: 2019/3/17
 * description : 接口权限公共业务
 *
 * @author : zhencai.cheng
 */
@Service
public class CommonService {

    private AccountRolePermissionDao accountRolePermissionDao;
    private AccountDao accountDao;

    @Autowired
    public CommonService(AccountRolePermissionDao accountRolePermissionDao, AccountDao accountDao) {
        this.accountRolePermissionDao = accountRolePermissionDao;
        this.accountDao = accountDao;
    }

    public String getRole() {
        return null;
    }

    public boolean checkPermission(Map<String, String> queryMap) {
        return accountRolePermissionDao.checkPermission(queryMap);
    }

    public boolean checkPermission(String permissionSign) {
        Map<String, String> queryMap = MapKit.newMap(2);
        queryMap.put("account", RequestThreadContext.getAccount());
        queryMap.put("permissionSign", permissionSign);
        return this.checkPermission(queryMap);
    }

    public List<Account> findAccountByDepartmentSign(String departmentSign) {
        return null;
    }

    public String currentAccountName() throws BusinessException {
        String ac = RequestThreadContext.getAccount();
        if (StringUtils.isBlank(ac)) {
            throw new BusinessException("not found account", true);
        }
        Account account = accountDao.findByAccount(RequestThreadContext.getAccount());
        return account.getRealName();
    }
}
