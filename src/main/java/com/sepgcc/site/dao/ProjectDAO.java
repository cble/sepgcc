package com.sepgcc.site.dao;

import com.sepgcc.site.dao.entity.ProjectDO;

import java.util.List;

public interface ProjectDAO {

    ProjectDO loadById(int id, List<Integer> status);
    List<ProjectDO> queryWithLimit(int userGroup, List<Integer> status, int index, int limit);
    int countAll(int userGroup, List<Integer> status);
    int insert(ProjectDO projectDO);
    boolean updateStatusById(int id, int status);
    boolean update(ProjectDO projectDO);
}
