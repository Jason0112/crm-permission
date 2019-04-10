package com.university.crm.web.action;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.system.Account;
import com.university.crm.constant.Constant;
import com.university.crm.constant.ResponseCode;
import com.university.crm.exception.BusinessException;
import com.university.crm.service.AccountService;
import com.university.crm.service.CommonService;
import com.university.crm.service.MenuService;
import com.university.crm.util.CookieKit;
import com.university.crm.util.HashKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
@Controller
public class LoginAction {
    private final Logger logger = LoggerFactory.getLogger(LoginAction.class);
    private AccountService accountService;
    private MenuService menuService;
    private CommonService commonService;

    @Autowired
    public LoginAction(AccountService accountService,
                       MenuService menuService,
                       CommonService commonService) {
        this.accountService = accountService;
        this.menuService = menuService;
        this.commonService = commonService;
    }

    /**
     * 登录
     */
    @RequestMapping("/inner/login")
    public String login(Account account, ModelMap map, HttpServletRequest request) throws BusinessException {

        try {
            accountService.login(account, request);
            //保存cookie
            CookieKit.updateCookie(Constant.authCookieName,
                    account.getAccount() + '|' + HashKit.md5HashAddSalt(account.getAccount(), Constant.authWebKey),
                    Constant.authCookiePath,
                    Constant.authCookieDomain,
                    Constant.cookieMaxAge);
            return "redirect:/member/home.action";
        } catch (BusinessException e) {
            map.put("errorCode", e.getErrorCode());
            map.put("msg", e.getMessage());
        } catch (Exception e) {
            logger.error("{}", e);
            map.put("errorCode", ResponseCode.REQUEST_ERROR_PROGRAM_EXCEPTION);
            map.put("msg", "系统异常，请联系管理员");
        }
        return "login";
    }


    /**
     * 主页
     */
    @RequestMapping("/member/home")
    public ModelAndView home() throws BusinessException {
        ModelAndView view = new ModelAndView("home");
        List<JSONObject> menuList = menuService.findMenuList();
        view.addObject("menuList", menuList);
        view.addObject("realName", commonService.currentAccountName());
        return view;
    }
}
