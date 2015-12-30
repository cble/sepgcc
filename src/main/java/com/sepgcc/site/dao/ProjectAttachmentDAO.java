package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectAttachmentDO;

import java.util.List;

public interface ProjectAttachmentDAO {

    List<ProjectAttachmentDO> queryByProjectId(int projectId);
    int insert(ProjectAttachmentDO projectAttachmentDO);
    boolean deleteByProjectId(int projectId);
}
