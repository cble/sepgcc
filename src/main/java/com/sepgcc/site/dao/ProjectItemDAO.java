package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectItemDO;

import java.util.List;

public interface ProjectItemDAO {
    ProjectItemDO loadById(int id);
    List<ProjectItemDO> queryByProjectId(int projectId);
    int insert(ProjectItemDO projectItemDO);
    boolean deleteByProjectId(int projectId);
}
