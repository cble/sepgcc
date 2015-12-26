package com.sepgcc.site.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sepgcc.site.dao.ProjectContactDAO;
import com.sepgcc.site.dao.ProjectDAO;
import com.sepgcc.site.dao.ProjectItemDAO;
import com.sepgcc.site.dao.entity.ProjectContactDO;
import com.sepgcc.site.dao.entity.ProjectDO;
import com.sepgcc.site.dao.entity.ProjectItemDO;
import com.sepgcc.site.dto.Project;
import com.sepgcc.site.dto.ProjectContact;
import com.sepgcc.site.dto.ProjectItem;
import com.sepgcc.site.utils.ProjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Resource
    private ProjectDAO projectDAO;
    @Resource
    private ProjectItemDAO projectItemDAO;
    @Resource
    private ProjectContactDAO projectContactDAO;

    public Project loadById(int projectId) {
        ProjectDO projectDO = projectDAO.loadById(projectId);
        Project project = ProjectUtils.toProject(projectDO);
        if (project != null) {
            project.setProjectItemList(queryProjectItemList(projectId));
            project.setProjectContactList(queryProjectContactList(projectId));
        }
        return project;
    }

    public List<ProjectItem> queryProjectItemList(int projectId) {
        List<ProjectItemDO> projectItemDOs = projectItemDAO.queryByProjectId(projectId);
        return Lists.transform(projectItemDOs, new Function<ProjectItemDO, ProjectItem>() {
            @Override
            public ProjectItem apply(ProjectItemDO projectItemDO) {
                return ProjectUtils.toProjectItem(projectItemDO);
            }
        });
    }

    public List<ProjectContact> queryProjectContactList(int projectId) {
        List<ProjectContactDO> projectContactDOs = projectContactDAO.queryByProjectId(projectId);
        return Lists.transform(projectContactDOs, new Function<ProjectContactDO, ProjectContact>() {
            @Override
            public ProjectContact apply(ProjectContactDO projectContactDO) {
                return ProjectUtils.toProjectContact(projectContactDO);
            }
        });
    }

    public List<Project> queryAll() {
        List<Project> resultList = new ArrayList<Project>();
        List<ProjectDO> projectDOs = projectDAO.queryAll();
        for (ProjectDO projectDO : projectDOs) {
            Project project = ProjectUtils.toProject(projectDO);
            if (project != null) {
                project.setProjectItemList(queryProjectItemList(projectDO.getId()));
                project.setProjectContactList(queryProjectContactList(projectDO.getId()));
            }
            resultList.add(project);
        }
        return resultList;
    }

    public List<Project> queryWithLimit(int index, int limit) {
        List<Project> resultList = new ArrayList<Project>();
        List<ProjectDO> projectDOs = projectDAO.queryWithLimit(index, limit);
        for (ProjectDO projectDO : projectDOs) {
            Project project = ProjectUtils.toProject(projectDO);
            if (project != null) {
                project.setProjectItemList(queryProjectItemList(projectDO.getId()));
                project.setProjectContactList(queryProjectContactList(projectDO.getId()));
            }
            resultList.add(project);
        }
        return resultList;
    }

    public int countAll() {
        return projectDAO.countAll();
    }
}
