package com.sepgcc.site.controller;

import com.sepgcc.site.dto.User;
import com.sepgcc.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/login")
    public ModelAndView login(
            String username,
            String password,
            String redirect, HttpServletRequest request) throws Exception {
        User user = UserUtils.getUser(request.getSession());
        if (user != null) {
            if (StringUtils.isNotBlank(redirect)) {
                return new ModelAndView("redirect:" + redirect);
            } else {
                return new ModelAndView(new RedirectView("/index"));
            }
        } else if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            if ("test".equals(username)) {
                UserUtils.setUser(request.getSession(), new User());
                return new ModelAndView(new RedirectView("/index"));
            } else {
                return new ModelAndView(new RedirectView("/login"), new HashMap<String, Object>());
            }
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserUtils.removeUser(request.getSession());
        return new ModelAndView(new RedirectView("/login"));
    }
}
