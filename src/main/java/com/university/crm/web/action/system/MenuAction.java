package com.university.crm.web.action.system;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.system.Menu;
import com.university.crm.bean.base.Page;
import com.university.crm.bean.base.ResponseJson;
import com.university.crm.exception.BusinessException;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.data.dto.search.MenuSearch;
import com.university.crm.service.MenuService;
import com.university.crm.util.ValidatorKit;
import com.university.crm.web.action.common.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * date: 2019/3/17
 * description : 菜单
 *
 * @author : zhencai.cheng
 */
@RestController
@RequestMapping("/member/menu")
public class MenuAction extends BaseAction {

    private MenuService menuService;

    @Autowired
    public MenuAction(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 更新 、插入
     */
    @RequestMapping("/upsert")
    public ResponseJson upsert(Menu menu) throws BusinessException {
        ValidatorKit.validation(menu);
        menuService.upsert(menu);
        return wrapperJson();
    }


    /**
     * 列表
     */
    @RequestMapping("/findTableList")
    public ResponseJson findTableList(MenuSearch search, Page page) {
        PageInfo<JSONObject> info = menuService.findTableList(search, page);
        return wrapperList(info);
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseJson delete(Integer menuID) throws BusinessException {
        menuService.delete(menuID);
        return wrapperJson();
    }

    /**
     * 详情
     */
    @RequestMapping("/info")
    public ResponseJson info(Integer menuID) throws BusinessException {
        Menu menu =menuService.findMenu(menuID);
        return wrapperJson(menu);
    }
}
