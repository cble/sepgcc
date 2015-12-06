package com.sepgcc.site.utils;

import com.sepgcc.site.constants.SessionConstants;
import com.sepgcc.site.dao.entity.UserDO;
import com.sepgcc.site.dto.User;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;

public class UserUtils {

    private static final String PWD_SALT = "";

    private static final BeanCopier toUserCopier = BeanCopier.create(UserDO.class, User.class, false);
    private static final BeanCopier toUserDOCopier = BeanCopier.create(User.class, UserDO.class, false);

    public static User toUser(UserDO userDO) {
        User result = null;
        if (userDO != null) {
            result = new User();
            toUserCopier.copy(userDO, result, null);
        }
        return result;
    }

    public static UserDO toUserDO(User user) {
        UserDO result = null;
        if (user != null) {
            result = new UserDO();
            toUserDOCopier.copy(user, result, null);
        }
        return result;
    }

    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex((PWD_SALT + password).getBytes());
    }

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
