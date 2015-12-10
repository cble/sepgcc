package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectDAO;
import com.sepgcc.site.dao.entity.ProjectDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ProjectDAOImpl extends SqlMapClientDaoSupport implements ProjectDAO {

    @Override
    public ProjectDO loadById(int id) {
        return (ProjectDO) this.getSqlMapClientTemplate().queryForObject("project.loadById", id);
    }
}
