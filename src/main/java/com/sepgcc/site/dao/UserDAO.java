package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.UserDO;

public interface UserDAO {
    UserDO loadByUsername(String username);
}
