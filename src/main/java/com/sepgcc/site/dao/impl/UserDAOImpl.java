package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.UserDAO;
import com.sepgcc.site.dao.entity.UserDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.Map;

public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO {

    @Override
    public UserDO loadByUsername(String username) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", username);
        return (UserDO) this.getSqlMapClientTemplate().queryForObject("user.loadByUsername", param);
    }
}
