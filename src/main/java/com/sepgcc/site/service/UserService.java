package com.sepgcc.site.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sepgcc.site.dao.UserDAO;
import com.sepgcc.site.dao.entity.UserDO;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.utils.SecurityUtils;
import com.sepgcc.site.utils.UserUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

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

    public int batchCreateUser(List<User> userList) {
        int successNumber = 0;
        for (User user : userList) {
            try {
                UserDO userDO = UserUtils.toUserDO(user);
                userDO.setPassword(SecurityUtils.encryptPassword(UserUtils.generateDefaultPassword(user)));
                int insertId = userDAO.insert(userDO);
                if (insertId > 0) {
                    successNumber++;
                }
            } catch (Exception e) {
                log.error("batchCreateUser error", e);
            }
        }
        return successNumber;
    }

    public String changePassword(int userId, String oldPwd, String newPwd) {
        UserDO userDO = userDAO.loadById(userId);
        if (userDO != null) {
            String encryptPassword = SecurityUtils.encryptPassword(oldPwd);
            if (encryptPassword.equals(userDO.getPassword())) {
                boolean updateSuccess = userDAO.updatePassword(userId, SecurityUtils.encryptPassword(newPwd));
                return updateSuccess ? null : "系统错误，请稍后再试";
            } else {
                return "原密码错误";
            }
        } else {
            return "用户不存在";
        }
    }
}
