package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.UploadDO;
import com.sepgcc.site.dto.Upload;

import java.util.List;

public interface UploadDAO {
    int insert(UploadDO uploadDO);
    UploadDO loadById(int id);
    List<UploadDO> queryByUserIdWithLimit(int userId, int index, int limit);
    int countByUserId(int userId);
    List<UploadDO> queryByProjectId(int projectId);
    int countByProjectId(int projectId);
}
