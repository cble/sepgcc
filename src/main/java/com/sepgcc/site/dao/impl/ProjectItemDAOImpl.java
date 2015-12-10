package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectItemDAO;
import com.sepgcc.site.dao.entity.ProjectItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectItemDAOImpl extends SqlMapClientDaoSupport implements ProjectItemDAO {

    @Override
    public List<ProjectItemDO> queryByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().queryForList("projectItem.queryByProjectId", projectId);
    }
}
