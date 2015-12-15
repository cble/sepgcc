package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SecurityConstants;
import com.sepgcc.site.constants.SessionConstants;
import com.sepgcc.site.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @ModelAttribute
    public Integer getUserId(HttpServletRequest request) {
        Object object = request.getAttribute(SecurityConstants.REQUEST_USERID);
        return object != null ? (Integer) object : null;
    }

    public void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(SessionConstants.SESSION_USER, user);
    }
}
