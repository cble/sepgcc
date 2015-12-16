package com.sepgcc.site.service;

import com.sepgcc.site.dao.UserDAO;
import com.sepgcc.site.dao.entity.UserDO;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.utils.SecurityUtils;
import com.sepgcc.site.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDAO userDAO;

    public User loadUser(String username, String password) {
        User user = null;
        UserDO userDO = userDAO.loadByUsername(username);
        if (userDO != null) {
            String encryptPassword = SecurityUtils.encryptPassword(password);
            if (encryptPassword.equals(userDO.getPassword())) {
                user = UserUtils.toUser(userDO);
            }
        }
        return user;
    }
}
