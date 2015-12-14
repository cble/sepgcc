package com.sepgcc.site.dao.impl;

import com.sepgcc.site.dao.FileMetaDAO;
import com.sepgcc.site.dao.entity.FileMetaDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FileMetaDAOImpl extends SqlMapClientDaoSupport implements FileMetaDAO {

    @Override
    public FileMetaDO loadById(String fileId) {
        return (FileMetaDO) this.getSqlMapClientTemplate().queryForObject("fileMeta.loadById", fileId);
    }

    @Override
    public int insert(FileMetaDO fileMetaDO) {
        Object insert = this.getSqlMapClientTemplate().insert("fileMeta.insert", fileMetaDO);
        return insert != null ? (Integer) insert : 0;
    }
}
