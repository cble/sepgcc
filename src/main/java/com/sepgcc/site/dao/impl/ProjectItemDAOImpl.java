package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectItemDAO;
import com.sepgcc.site.dao.entity.ProjectItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectItemDAOImpl extends SqlMapClientDaoSupport implements ProjectItemDAO {

    @Override
    public ProjectItemDO loadById(int id) {
        return (ProjectItemDO) this.getSqlMapClientTemplate().queryForObject("projectItem.loadById", id);
    }

    @Override
    public List<ProjectItemDO> queryByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().queryForList("projectItem.queryByProjectId", projectId);
    }

    @Override
    public int insert(ProjectItemDO projectItemDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectItem.insert", projectItemDO);
        return insert != null ? (Integer) insert : 0;
    }

}
