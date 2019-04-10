package com.university.crm.web.action.common;


import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.base.ResponseJson;
import com.university.crm.constant.Constant;
import com.university.crm.interceptor.mybatis.PageInfo;
import com.university.crm.util.MapKit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * date: 2018/9/23
 * description :所有页面响应体
 *
 * @author : zhencai.cheng
 */
@Controller
public class BaseAction {

    /**
     * 封装列表返回
     *
     * @param pageInfo 分页列表封装
     * @return 列表数据
     */
    protected ResponseJson wrapperList(PageInfo<JSONObject> pageInfo) {
        return wrapper(pageInfo);
    }


    protected ResponseJson wrapper(Object result) {
        if (result == null) {
            return new ResponseJson();
        }
        if (result instanceof PageInfo) {
            PageInfo pageInfo = (PageInfo) result;
            Map<String, Object> resultMap = MapKit.newMap(3);
            resultMap.put(Constant.TOTAL, pageInfo.getTotal());
            resultMap.put(Constant.ROWS, pageInfo.getList());
            resultMap.put(Constant.STATUS, HttpStatus.OK);
            return new ResponseJson(resultMap);
        } else {
            return new ResponseJson(result);
        }
    }


    /**
     * 页面响应数据
     *
     * @return 响应体
     */
    protected ResponseJson wrapperJson() {
        return wrapperJson(null);
    }

    protected ResponseJson wrapperJson(Object result) {
        return wrapper(result);
    }

}
