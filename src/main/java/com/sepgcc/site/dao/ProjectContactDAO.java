package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectContactDO;

import java.util.List;

public interface ProjectContactDAO {

    List<ProjectContactDO> queryByProjectId(int projectId);
}
