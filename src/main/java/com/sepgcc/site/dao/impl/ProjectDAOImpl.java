package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectDAO;
import com.sepgcc.site.dao.entity.ProjectDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDAOImpl extends SqlMapClientDaoSupport implements ProjectDAO {

    @Override
    public ProjectDO loadById(int id, List<Integer> status) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put("status", status);
        return (ProjectDO) this.getSqlMapClientTemplate().queryForObject("project.loadById", param);
    }

    @Override
    public List<ProjectDO> queryWithLimit(int userGroup, List<Integer> status, int index, int limit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("status", status);
        param.put("userGroup", userGroup);
        param.put("index", index);
        param.put("limit", limit);
        return this.getSqlMapClientTemplate().queryForList("project.queryWithLimit", param);
    }

    @Override
    public int countAll(int userGroup, List<Integer> status) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("status", status);
        param.put("userGroup", userGroup);
        return (Integer) this.getSqlMapClientTemplate().queryForObject("project.countAll", param);
    }

    @Override
    public int insert(ProjectDO projectDO) {
        Object insert = this.getSqlMapClientTemplate().insert("project.insert", projectDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public boolean updateStatusById(int id, int status) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put("status", status);
        return this.getSqlMapClientTemplate().update("project.updateStatusById", param) > 0;
    }

    @Override
    public boolean update(ProjectDO projectDO) {
        return this.getSqlMapClientTemplate().update("project.update", projectDO) > 0;
    }
}
