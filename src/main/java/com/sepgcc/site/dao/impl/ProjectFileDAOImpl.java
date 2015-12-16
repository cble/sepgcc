package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectFileDAO;
import com.sepgcc.site.dao.entity.ProjectFileDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ProjectFileDAOImpl extends SqlMapClientDaoSupport implements ProjectFileDAO {

    @Override
    public int insert(ProjectFileDO projectFileDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectFile.insert", projectFileDO);
        return insert != null ? (Integer) insert : 0;
    }
}
