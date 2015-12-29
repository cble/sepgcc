package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectDO;

import java.util.List;

public interface ProjectDAO {

    ProjectDO loadById(int id, List<Integer> status);
    List<ProjectDO> queryWithLimit(List<Integer> status, int index, int limit);
    int countAll(List<Integer> status);
    int insert(ProjectDO projectDO);
}
