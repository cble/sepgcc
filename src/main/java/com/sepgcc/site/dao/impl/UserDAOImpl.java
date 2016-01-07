package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.UserDAO;
import com.sepgcc.site.dao.entity.UserDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO {

    @Override
    public UserDO loadByUsername(String username) {
        return (UserDO) this.getSqlMapClientTemplate().queryForObject("user.loadByUsername", username);
    }

    @Override
    public UserDO loadById(int id) {
        return (UserDO) this.getSqlMapClientTemplate().queryForObject("user.loadById", id);
    }

    @Override
    public List<UserDO> queryWithLimit(int index, int limit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("index", index);
        param.put("limit", limit);
        return this.getSqlMapClientTemplate().queryForList("user.queryWithLimit", param);
    }

    @Override
    public int countAll() {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("user.countAll");
    }

    @Override
    public int insert(UserDO userDO) {
        Object insert = this.getSqlMapClientTemplate().insert("user.insert", userDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public boolean updatePassword(int userId, String password) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("password", password);
        return this.getSqlMapClientTemplate().update("user.updatePassword", param) > 0;
    }
}
