package com.university.crm.web.action.system;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.base.ResponseJson;
import com.university.crm.bean.system.Permission;
import com.university.crm.data.dto.search.PermissionSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.service.PermissionService;
import com.university.crm.util.ValidatorKit;
import com.university.crm.web.action.common.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * date: 2019/3/17
 * description : 权限
 *
 * @author : zhencai.cheng
 */
@RestController
@RequestMapping("/member/permission")
public class PermissionAction extends BaseAction {


    private PermissionService permissionService;

    @Autowired
    public PermissionAction(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 更新 、插入
     */
    @RequestMapping("/upsert")
    public ResponseJson upsert(Permission permission) throws BusinessException {
        ValidatorKit.validation(permission);
        permissionService.upsert(permission);
        return wrapperJson();
    }

    /**
     * 列表
     */
    @RequestMapping("/findTableList")
    public ResponseJson findTableList(PermissionSearch search, Page page) {
        PageInfo<JSONObject> info = permissionService.findTableList(search, page);
        return wrapperList(info);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseJson delete(Integer permissionID) throws BusinessException {
        permissionService.delete(permissionID);
        return wrapperJson();
    }

    /**
     * 详情
     */
    @RequestMapping("/info")
    public ResponseJson info(Integer permissionID) throws BusinessException {
        Permission permission = permissionService.findPermission(permissionID);
        return wrapperJson(permission);
    }
}
