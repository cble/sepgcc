package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectFileDAO;
import com.sepgcc.site.dao.entity.ProjectFileDO;
import com.sepgcc.site.dto.Upload;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectFileDAOImpl extends SqlMapClientDaoSupport implements ProjectFileDAO {

    @Override
    public int insert(ProjectFileDO projectFileDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectFile.insert", projectFileDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public List<ProjectFileDO> queryByUploadId(int uploadId) {
        return this.getSqlMapClientTemplate().queryForList("projectFile.queryByUploadId", uploadId);
    }

    @Override
    public boolean deleteByUploadId(int uploadId) {
        return this.getSqlMapClientTemplate().delete("projectFile.deleteByUploadId", uploadId) > 0;
    }
}
