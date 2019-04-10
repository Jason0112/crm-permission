package com.university.crm.web.action.common;


import com.university.crm.exception.BusinessException;
import com.university.crm.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * date: 2019/03/19
 * description : 所有页面请求公司方法
 *
 * @author : zhencai.cheng
 */
@Controller
public class CommonAction {

    private CommonService commonService;

    @Autowired
    public CommonAction(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * 所有报表，公共请求页面
     * <p>
     * Case
     * 请求链接 ：member/wechat/report.action
     * <p>
     * 页面地址：sub/wechat-report
     *
     * @param path wechat
     * @param page report
     * @return 视图model
     */
    @RequestMapping("/manager/{path}/{page}.action")
    public ModelAndView page(@PathVariable("path") String path, @PathVariable("page") String page, HttpServletRequest request) throws BusinessException {
        ModelAndView view = new ModelAndView(path + "/" + page);
        this.builderAttribute(view, request);
        return view;
    }

    @RequestMapping("/manager/{page}.action")
    public ModelAndView page(@PathVariable("page") String page, HttpServletRequest request) throws BusinessException {
        ModelAndView view = new ModelAndView(page);
        this.builderAttribute(view, request);
        return view;
    }

    @RequestMapping("/member/{page}.action")
    public ModelAndView index(@PathVariable("page") String page, HttpServletRequest request) throws BusinessException {
        ModelAndView view = new ModelAndView(page);
        this.builderAttribute(view, request);
        return view;
    }

    @RequestMapping("/{page}.action")
    public ModelAndView loginIndex(@PathVariable("page") String page, HttpServletRequest request) throws BusinessException {
        ModelAndView view = new ModelAndView(page);
        this.builderAttribute(view, request);
        return view;
    }

    /**
     * 参数透传
     *
     * @param view    视图
     * @param request 请求
     */
    private void builderAttribute(ModelAndView view, HttpServletRequest request) throws BusinessException {
        String role = commonService.getRole();
        Map<String, String[]> requestMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestMap.entrySet()) {
            String[] value = entry.getValue();
            if (value != null) {
                switch (value.length) {
                    case 0:
                        break;
                    case 1:
                        view.addObject(entry.getKey(), value[0]);
                        break;
                    default:
                        view.addObject(entry.getKey(), value);
                        break;
                }
            }
        }
        view.addObject("role", role);
        view.addObject("version", System.currentTimeMillis());
    }
}
