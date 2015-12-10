package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectContactDAO;
import com.sepgcc.site.dao.entity.ProjectContactDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectContactDAOImpl extends SqlMapClientDaoSupport implements ProjectContactDAO {

    @Override
    public List<ProjectContactDO> queryByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().queryForList("projectContact.queryByProjectId", projectId);
    }
}
