package com.university.crm.web.action.system;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.base.ResponseJson;
import com.university.crm.bean.system.Role;
import com.university.crm.data.dto.RoleBind;
import com.university.crm.data.dto.search.AccountSearch;
import com.university.crm.data.dto.search.RoleSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.service.RoleService;
import com.university.crm.util.ValidatorKit;
import com.university.crm.web.action.common.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * date: 2019/3/17
 * description : 角色
 *
 * @author : zhencai.cheng
 */
@RestController
@RequestMapping("/member/role")
public class RoleAction extends BaseAction {

    private RoleService roleService;

    @Autowired
    public RoleAction(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 更新 、插入
     */
    @RequestMapping("/upsert")
    public ResponseJson upsert(Role role) throws BusinessException {
        ValidatorKit.validation(role);
        roleService.upsert(role);
        return wrapperJson();
    }


    /**
     * 列表
     */
    @RequestMapping("/findTableList")
    public ResponseJson findTableList(RoleSearch search, Page page) {
        PageInfo<JSONObject> info = roleService.findTableList(search, page);
        return wrapperList(info);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseJson delete(Integer roleID) throws BusinessException {
        roleService.delete(roleID);
        return wrapperJson();
    }

    /**
     * 详情
     */
    @RequestMapping("/info")
    public ResponseJson info(Integer roleID) throws BusinessException {
        JSONObject info = roleService.findRolePermission(roleID);
        return wrapperJson(info);
    }

    /**
     * 绑定权限
     */
    @RequestMapping("/bind")
    public ResponseJson bind(RoleBind bind) throws BusinessException {
        ValidatorKit.validation(bind);
        roleService.bindPermission(bind);
        return wrapperJson();
    }
}
