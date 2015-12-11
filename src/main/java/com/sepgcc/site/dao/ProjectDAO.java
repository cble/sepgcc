package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectDO;

import java.util.List;

public interface ProjectDAO {

    ProjectDO loadById(int id);
    List<ProjectDO> queryAll();
    int countAll();
}
