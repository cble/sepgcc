package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectDAO;
import com.sepgcc.site.dao.entity.ProjectDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDAOImpl extends SqlMapClientDaoSupport implements ProjectDAO {

    @Override
    public ProjectDO loadById(int id) {
        return (ProjectDO) this.getSqlMapClientTemplate().queryForObject("project.loadById", id);
    }

    @Override
    public List<ProjectDO> queryAll() {
        return this.getSqlMapClientTemplate().queryForList("project.queryAll");
    }

    @Override
    public List<ProjectDO> queryWithLimit(int index, int limit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("index", index);
        param.put("limit", limit);
        return this.getSqlMapClientTemplate().queryForList("project.queryWithLimit", param);
    }

    @Override
    public int countAll() {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("project.countAll");
    }
}
