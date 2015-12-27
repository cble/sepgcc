package com.sepgcc.site.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sepgcc.site.dao.UserDAO;
import com.sepgcc.site.dao.entity.UserDO;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.utils.SecurityUtils;
import com.sepgcc.site.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
                user = UserUtils.toUser(userDO, true);
            }
        }
        return user;
    }

    public User loadById(int id) {
        User user = null;
        UserDO userDO = userDAO.loadById(id);
        if (userDO != null) {
            user = UserUtils.toUser(userDO, true);
        }
        return user;
    }

    public List<User> queryWithLimit(int index, int limit) {
        return Lists.transform(userDAO.queryWithLimit(index, limit), new Function<UserDO, User>() {
            @Override
            public User apply(UserDO userDO) {
                return UserUtils.toUser(userDO, false);
            }
        });
    }

    public int countAll() {
        return userDAO.countAll();
    }
}
