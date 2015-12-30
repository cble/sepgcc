package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectContactDAO;
import com.sepgcc.site.dao.entity.ProjectContactDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectContactDAOImpl extends SqlMapClientDaoSupport implements ProjectContactDAO {

    @Override
    public ProjectContactDO loadById(int id) {
        return (ProjectContactDO) this.getSqlMapClientTemplate().queryForObject("projectContact.loadById", id);
    }

    @Override
    public List<ProjectContactDO> queryByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().queryForList("projectContact.queryByProjectId", projectId);
    }

    @Override
    public int insert(ProjectContactDO projectContactDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectContact.insert", projectContactDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public boolean deleteByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().delete("projectContact.deleteByProjectId", projectId) > 0;
    }
}
