package com.university.crm.web.action.system;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.system.Account;
import com.university.crm.data.dto.AccountBind;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.base.ResponseJson;
import com.university.crm.data.dto.search.AccountSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.service.AccountService;
import com.university.crm.util.ValidatorKit;
import com.university.crm.web.action.common.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * date: 2019/3/27
 * description : 账号
 *
 * @author : zhencai.cheng
 */
@RestController
@RequestMapping("/member/account")
public class AccountAction extends BaseAction {

    private AccountService accountService;

    @Autowired
    public AccountAction(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 更新 、插入
     */
    @RequestMapping("/upsert")
    public ResponseJson upsert(Account account) throws BusinessException {
        ValidatorKit.validation(account);
        accountService.upsert(account);
        return wrapperJson();
    }


    /**
     * 列表
     */
    @RequestMapping("/findTableList")
    public ResponseJson findTableList(AccountSearch search, Page page) {
        PageInfo<JSONObject> info = accountService.findTableList(search, page);
        return wrapperList(info);
    }


    /**
     * 详情
     */
    @RequestMapping("/info")
    public ResponseJson info(Integer accountID) throws BusinessException {
        JSONObject info =accountService.findAccountRole(accountID);
        return wrapperJson(info);
    }


    /**
     * 绑定角色
     */
    @RequestMapping("/bind")
    public ResponseJson bind(AccountBind bind) throws BusinessException {
        ValidatorKit.validation(bind);
        accountService.bindRole(bind);
        return wrapperJson();
    }
}
