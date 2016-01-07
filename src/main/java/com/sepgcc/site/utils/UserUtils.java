package com.sepgcc.site.utils;

import com.sepgcc.site.constants.SecurityConstants;
import com.sepgcc.site.dao.entity.UserDO;
import com.sepgcc.site.dto.User;
import net.sf.cglib.beans.BeanCopier;

public class UserUtils {

    private static final BeanCopier toUserCopier = BeanCopier.create(UserDO.class, User.class, false);
    private static final BeanCopier toUserDOCopier = BeanCopier.create(User.class, UserDO.class, false);

    public static User toUser(UserDO userDO, boolean fillToken) {
        User result = null;
        if (userDO != null) {
            result = new User();
            toUserCopier.copy(userDO, result, null);
            if (fillToken) {
                result.setToken(SecurityUtils.generateToken(result));
            }
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

    public static String generateDefaultPassword(User user) {
        return SecurityConstants.DEFAULT_PASSWORD;
    }
}
