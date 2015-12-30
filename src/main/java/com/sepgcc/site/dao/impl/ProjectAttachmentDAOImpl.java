package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectAttachmentDAO;
import com.sepgcc.site.dao.entity.ProjectAttachmentDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectAttachmentDAOImpl extends SqlMapClientDaoSupport implements ProjectAttachmentDAO {

    @Override
    public List<ProjectAttachmentDO> queryByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().queryForList("projectAttachment.queryByProjectId", projectId);
    }

    @Override
    public int insert(ProjectAttachmentDO projectAttachmentDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectAttachment.insert", projectAttachmentDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public boolean deleteByProjectId(int projectId) {
        return this.getSqlMapClientTemplate().delete("projectAttachment.deleteByProjectId", projectId) > 0;
    }
}
