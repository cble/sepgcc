package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectContactDO;

import java.util.List;

public interface ProjectContactDAO {

    ProjectContactDO loadById(int id);
    List<ProjectContactDO> queryByProjectId(int projectId);
    int insert(ProjectContactDO projectContactDO);
}
