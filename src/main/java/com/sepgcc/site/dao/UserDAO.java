package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.UserDO;

import java.util.List;

public interface UserDAO {

    UserDO loadByUsername(String username);
    UserDO loadById(int id);
    List<UserDO> queryWithLimit(int index, int limit);
    int countAll();
    int insert(UserDO userDO);
}
