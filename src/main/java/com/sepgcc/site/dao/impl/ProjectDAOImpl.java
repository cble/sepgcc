package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectDAO;
import com.sepgcc.site.dao.entity.ProjectDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

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
    public int countAll() {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("project.countAll");
    }
}
