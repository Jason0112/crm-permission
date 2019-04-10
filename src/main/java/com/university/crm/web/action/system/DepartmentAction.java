package com.university.crm.web.action.system;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.system.Department;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.base.ResponseJson;
import com.university.crm.data.dto.search.DepartmentSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.service.DepartmentService;
import com.university.crm.util.ValidatorKit;
import com.university.crm.web.action.common.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * date: 2019/3/27
 * description : 部门
 *
 * @author : zhencai.cheng
 */
@RestController
@RequestMapping("/member/department")
public class DepartmentAction extends BaseAction {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentAction(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    /**
     * 更新 、插入
     */
    @RequestMapping("/upsert")
    public ResponseJson upsert(Department department) throws BusinessException {
        ValidatorKit.validation(department);
        departmentService.upsert(department);
        return wrapperJson();
    }


    /**
     * 列表
     */
    @RequestMapping("/findTableList")
    public ResponseJson findTableList(DepartmentSearch search, Page page) {
        PageInfo<JSONObject> info = departmentService.findTableList(search, page);
        return wrapperList(info);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseJson delete(Integer departmentID) throws BusinessException {
        departmentService.deleteDepartment(departmentID);
        return wrapperJson();
    }

    /**
     * 详情
     */
    @RequestMapping("/info")
    public ResponseJson info(Integer departmentID) throws BusinessException {
        JSONObject info = departmentService.findDepartmentByID(departmentID);
        return wrapperJson(info);
    }
}
