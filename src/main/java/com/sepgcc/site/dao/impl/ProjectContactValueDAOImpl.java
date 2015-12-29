package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectContactValueDAO;
import com.sepgcc.site.dao.entity.ProjectContactValueDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class ProjectContactValueDAOImpl extends SqlMapClientDaoSupport implements ProjectContactValueDAO {


    @Override
    public int insert(ProjectContactValueDO projectContactValueDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectContactValue.insert", projectContactValueDO);
        return insert != null ? (Integer) insert : 0;
    }

    @Override
    public List<ProjectContactValueDO> queryByUploadId(int uploadId) {
        return this.getSqlMapClientTemplate().queryForList("projectContactValue.queryByUploadId", uploadId);
    }

    @Override
    public boolean deleteByUploadId(int uploadId) {
        return this.getSqlMapClientTemplate().delete("projectContactValue.deleteByUploadId", uploadId) > 0;
    }
}
