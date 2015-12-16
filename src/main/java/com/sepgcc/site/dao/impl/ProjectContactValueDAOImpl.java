package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.ProjectContactValueDAO;
import com.sepgcc.site.dao.entity.ProjectContactValueDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ProjectContactValueDAOImpl extends SqlMapClientDaoSupport implements ProjectContactValueDAO {


    @Override
    public int insert(ProjectContactValueDO projectContactValueDO) {
        Object insert = this.getSqlMapClientTemplate().insert("projectContactValue.insert", projectContactValueDO);
        return insert != null ? (Integer) insert : 0;
    }
}
