package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.FileMetaDO;

public interface FileMetaDAO {
    FileMetaDO loadById(String fileId);
    int insert(FileMetaDO fileMetaDO);
}
