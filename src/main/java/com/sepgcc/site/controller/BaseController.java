package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SecurityConstants;
import com.sepgcc.site.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @ModelAttribute
    public User getUser(HttpServletRequest request) {
        Object object = request.getAttribute(SecurityConstants.REQUEST_USER);
        return object != null ? (User) object : null;
    }

    public void setToken(HttpServletRequest request, String token) {
        request.getSession().setAttribute(SecurityConstants.SESSION_TOKEN, token);
    }
}
