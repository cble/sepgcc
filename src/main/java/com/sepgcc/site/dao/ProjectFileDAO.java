package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectFileDO;

import java.util.List;

public interface ProjectFileDAO {
    int insert(ProjectFileDO projectFileDO);
    List<ProjectFileDO> queryByUploadId(int uploadId);
    boolean deleteByUploadId(int uploadId);
}
