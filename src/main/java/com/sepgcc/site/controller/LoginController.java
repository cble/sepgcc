package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SecurityConstants;
import com.sepgcc.site.dto.AjaxResponse;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class LoginController extends BaseController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView login(
            @ModelAttribute User user,
            String username,
            String password,
            String captcha,
            String redirect,
            HttpServletRequest request) throws Exception {
        if (user != null) {
            if (StringUtils.isNotBlank(redirect)) {
                return new ModelAndView("redirect:" + redirect);
            } else {
                return new ModelAndView(new RedirectView("index"));
            }
        } else if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(captcha)) {
            String sessionCaptcha = getSessionCaptcha(request);
            if (!sessionCaptcha.equals(captcha)) {
                return new ModelAndView(new RedirectView("login"), new HashMap<String, Object>());
            }
            user = userService.loadUser(username, password);
            if (user != null && user.isEnable()) {
                setToken(request, user.getToken());
                request.getSession().removeAttribute(SecurityConstants.SESSION_CAPTCHA);
                return new ModelAndView(new RedirectView("index"));
            } else {
                return new ModelAndView(new RedirectView("login"), new HashMap<String, Object>());
            }
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) throws Exception {
        setToken(request, null);
        return new ModelAndView(new RedirectView("login"));
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public ModelAndView changePassword() {
        return new ModelAndView("changepassword");
    }

    @RequestMapping(value = "/ajax/changepassword", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Boolean> doChangePassword(@ModelAttribute User user, String oldPwd, String newPwd) {
        String err = userService.changePassword(user.getId(), oldPwd, newPwd);
        return new AjaxResponse<Boolean>(HttpStatus.OK.value(), err, err == null);
    }

    private String getSessionCaptcha(HttpServletRequest request) {
        Object captchaAttribute = request.getSession().getAttribute(SecurityConstants.SESSION_CAPTCHA);
        return captchaAttribute != null ? String.valueOf(captchaAttribute) : "";
    }
}
