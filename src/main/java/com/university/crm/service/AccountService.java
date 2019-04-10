package com.university.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.system.Account;
import com.university.crm.data.dto.AccountBind;
import com.university.crm.data.dto.search.AccountSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageHelper;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.persistence.dao.AccountDao;
import com.university.crm.persistence.dao.AccountRolePermissionDao;
import com.university.crm.persistence.dao.ViewDao;
import com.university.crm.util.HashKit;
import com.university.crm.util.RequestKit;
import com.university.crm.util.ValidatorKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * date: 2019/3/17
 * description : 账号业务
 *
 * @author : zhencai.cheng
 */
@Service
public class AccountService {

    private final AccountDao accountDao;
    private final AccountRolePermissionDao accountRolePermissionDao;
    private final ViewDao viewDao;

    @Autowired
    public AccountService(AccountDao accountDao, AccountRolePermissionDao accountRolePermissionDao,
                          ViewDao viewDao) {
        this.accountDao = accountDao;
        this.accountRolePermissionDao = accountRolePermissionDao;
        this.viewDao = viewDao;
    }

    public void login(Account account, HttpServletRequest request) throws BusinessException {
        if (StringUtils.isBlank(account.getAccount()) || StringUtils.isBlank(account.getPassword())) {
            throw new BusinessException("用户名或密码不能为空", true);
        }
        Account db = accountDao.findByAccount(account.getAccount());
        if (db == null) {
            throw new BusinessException("账号不存在", true);
        }

        if (!Objects.equals(1, db.getState())) {
            throw new BusinessException(-4, "账号已被禁用", true);
        }

        if (!Objects.equals(db.getPassword(), HashKit.md5HashAddSalt(account.getPassword(), db.getPasswordSalt()))) {
            throw new BusinessException(-5, "密码错误", true);
        }
        account.setPassword(null);
        account.setAccountID(db.getAccountID());
        account.setLastLoginIP(RequestKit.getRequestIP(request));
        account.setLastLoginTime(new Date());
        accountDao.update(account);
    }

    public void upsert(Account account) throws BusinessException {
        ValidatorKit.validation(account);
        Account db = accountDao.findByAccount(account.getAccount());
        if (db == null) {
            account.setPasswordSalt(HashKit.getCharAndNum(6));
            account.setPassword(HashKit.md5HashAddSalt(account.getPassword(), account.getPasswordSalt()));
            accountDao.insertAccount(account);
        } else {
            if (Objects.equals(account.getAccountID(), db.getAccountID())) {
                throw new BusinessException("账号不正确", true);
            }
            if (!Objects.equals(HashKit.md5HashAddSalt(account.getPassword(), db.getPasswordSalt()), db.getPassword())) {
                throw new BusinessException("密码正确", true);
            }
            accountDao.update(account);
        }
    }

    public JSONObject findAccountRole(Integer accountID) {
        return accountRolePermissionDao.findAccountRole(accountID);
    }

    public PageInfo<JSONObject> findTableList(AccountSearch search, Page page) {
        PageHelper.startPage(page);
        List<JSONObject> list = viewDao.findAccountTableList(search);
        return new PageInfo<>(list);
    }

    public void bindRole(AccountBind bind) throws BusinessException {
        ValidatorKit.validation(bind);
        accountRolePermissionDao.deleteAccountRoleRelationByAccountID(bind.getAccountID());
        accountRolePermissionDao.insertAccountRoleRelation(bind);
    }
}
