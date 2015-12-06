package com.sepgcc.utils;

import com.sepgcc.constants.SessionConstants;
import com.sepgcc.site.dto.User;

import javax.servlet.http.HttpSession;

public class UserUtils {

    public static User getUser(HttpSession httpSession) {
        Object object = httpSession.getAttribute(SessionConstants.SESSION_USER);
        return object != null ? (User) object : null;
    }

    public static void setUser(HttpSession httpSession, User user) {
        httpSession.setAttribute(SessionConstants.SESSION_USER, user);
    }

    public static void removeUser(HttpSession httpSession) {
        httpSession.removeAttribute(SessionConstants.SESSION_USER);
    }
}
