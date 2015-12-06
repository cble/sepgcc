package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SessionConstants;
import com.sepgcc.site.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @ModelAttribute
    public User getUser(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(SessionConstants.SESSION_USER);
        return object != null ? (User) object : null;
    }

    public void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(SessionConstants.SESSION_USER, user);
    }
}
