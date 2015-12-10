package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectItemDO;

import java.util.List;

public interface ProjectItemDAO {
    List<ProjectItemDO> queryByProjectId(int projectId);
}
