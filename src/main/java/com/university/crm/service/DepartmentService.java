package com.university.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.system.Account;
import com.university.crm.bean.system.Department;
import com.university.crm.bean.base.Page;
import com.university.crm.data.dto.search.DepartmentSearch;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageHelper;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.persistence.dao.DepartmentDao;
import com.university.crm.persistence.dao.ViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * date: 2019/3/27
 * description :部门业务
 *
 * @author : zhencai.cheng
 */
@Service
public class DepartmentService {

    private final DepartmentDao departmentDao;
    private final CommonService commonService;
    private final ViewDao viewDao;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao, CommonService commonService, ViewDao viewDao) {
        this.departmentDao = departmentDao;
        this.commonService = commonService;
        this.viewDao = viewDao;
    }

    public void upsert(Department department) throws BusinessException {
        Department db = departmentDao.findDepartmentBySign(department.getDepartmentSign());

        if (db == null) {
            departmentDao.insertDepartment(department);
        } else {
            if (Objects.equals(department.getDepartmentID(), db.getDepartmentID())) {
                departmentDao.updateDepartment(department);
            }
            throw new BusinessException("请求数据错误", true);
        }
    }

    public PageInfo<JSONObject> findTableList(DepartmentSearch search, Page page) {
        PageHelper.startPage(page);
        List<JSONObject> list = viewDao.findDepartmentTableList(search);
        return new PageInfo<>(list);
    }

    public void deleteDepartment(Integer departmentID) throws BusinessException {
        JSONObject department = departmentDao.findDepartmentByID(departmentID);
        List<Account> list = commonService.findAccountByDepartmentSign(department.getString("departmentSign"));
        if (list != null && !list.isEmpty()) {
            throw new BusinessException("当前部门下绑定账号不能删除", true);
        }
        departmentDao.deleteDepartment(departmentID);
    }

    public JSONObject findDepartmentByID(Integer departmentID) {
        return departmentDao.findDepartmentByID(departmentID);
    }
}
