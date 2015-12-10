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
import java.util.List;

@Service
public class ProjectService {

    @Resource
    private ProjectDAO projectDAO;
    @Resource
    private ProjectItemDAO projectItemDAO;
    @Resource
    private ProjectContactDAO projectContactDAO;

    public Project loadProjectById(int projectId) {
        ProjectDO projectDO = projectDAO.loadById(projectId);
        Project project = ProjectUtils.toProject(projectDO);
        if (project != null) {
            List<ProjectItemDO> projectItemDOs = projectItemDAO.queryByProjectId(projectId);
            project.setProjectItemList(Lists.transform(projectItemDOs, new Function<ProjectItemDO, ProjectItem>() {
                @Override
                public ProjectItem apply(ProjectItemDO projectItemDO) {
                    return ProjectUtils.toProjectItem(projectItemDO);
                }
            }));
            List<ProjectContactDO> projectContactDOs = projectContactDAO.queryByProjectId(projectId);
            project.setProjectContactList(Lists.transform(projectContactDOs, new Function<ProjectContactDO, ProjectContact>() {
                @Override
                public ProjectContact apply(ProjectContactDO projectContactDO) {
                    return ProjectUtils.toProjectContact(projectContactDO);
                }
            }));
        }
        return project;
    }
}
