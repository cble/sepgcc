package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.UploadDAO;
import com.sepgcc.site.dao.entity.UploadDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadDAOImpl extends SqlMapClientDaoSupport implements UploadDAO {

    @Override
    public int insert(UploadDO uploadDO) {
        Object insert = this.getSqlMapClientTemplate().insert("upload.insert", uploadDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public UploadDO loadById(int id) {
        return (UploadDO) this.getSqlMapClientTemplate().queryForObject("upload.loadById", id);
    }

    @Override
    public List<UploadDO> queryByUserIdWithLimit(int userId, int index, int limit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("index", index);
        param.put("limit", limit);
        return this.getSqlMapClientTemplate().queryForList("upload.queryByUserIdWithLimit", param);
    }

    @Override
    public int countByUser(int userId) {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("upload.countByUser", userId);
    }

    @Override
    public List<UploadDO> queryByProject(int projectId) {
        return this.getSqlMapClientTemplate().queryForList("upload.queryByProjectId", projectId);
    }
}