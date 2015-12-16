package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.UploadDAO;
import com.sepgcc.site.dao.entity.UploadDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UploadDAOImpl extends SqlMapClientDaoSupport implements UploadDAO {

    @Override
    public int insert(UploadDO uploadDO) {
        Object insert = this.getSqlMapClientTemplate().insert("upload.insert", uploadDO);
        return insert != null ? (Integer) insert : 0;
    }
}
