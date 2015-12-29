package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectContactValueDO;

import java.util.List;

public interface ProjectContactValueDAO {
    int insert(ProjectContactValueDO projectContactValueDO);
    List<ProjectContactValueDO> queryByUploadId(int uploadId);
    boolean deleteByUploadId(int uploadId);
}
