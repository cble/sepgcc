package com.sepgcc.site.controller;

import com.sepgcc.site.dto.User;
import com.sepgcc.site.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
            String redirect,
            HttpServletRequest request) throws Exception {
        if (user != null) {
            if (StringUtils.isNotBlank(redirect)) {
                return new ModelAndView("redirect:" + redirect);
            } else {
                return new ModelAndView(new RedirectView("index"));
            }
        } else if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            user = userService.loadUser(username, password);
            if (user != null) {
                setUser(request, user);
                return new ModelAndView(new RedirectView("index"));
            } else {
                return new ModelAndView(new RedirectView("login"), new HashMap<String, Object>());
            }
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) throws Exception {
        setUser(request, null);
        return new ModelAndView(new RedirectView("login"));
    }
}
