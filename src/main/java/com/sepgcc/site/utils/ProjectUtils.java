package com.sepgcc.site.utils;

import com.sepgcc.site.dao.entity.ProjectContactDO;
import com.sepgcc.site.dao.entity.ProjectDO;
import com.sepgcc.site.dao.entity.ProjectItemDO;
import com.sepgcc.site.dto.Project;
import com.sepgcc.site.dto.ProjectContact;
import com.sepgcc.site.dto.ProjectItem;
import net.sf.cglib.beans.BeanCopier;

public class ProjectUtils {

    private static final BeanCopier toProjectCopier = BeanCopier.create(ProjectDO.class, Project.class, false);
    private static final BeanCopier toProjectDOCopier = BeanCopier.create(Project.class, ProjectDO.class, false);
    private static final BeanCopier toProjectItemCopier = BeanCopier.create(ProjectItemDO.class, ProjectItem.class, false);
    private static final BeanCopier toProjectItemDOCopier = BeanCopier.create(ProjectItem.class, ProjectItemDO.class, false);
    private static final BeanCopier toProjectContactCopier = BeanCopier.create(ProjectContactDO.class, ProjectContact.class, false);
    private static final BeanCopier toProjectContactDOCopier = BeanCopier.create(ProjectContact.class, ProjectContactDO.class, false);

    public static Project toProject(ProjectDO projectDO) {
        Project result = null;
        if (projectDO != null) {
            result = new Project();
            toProjectCopier.copy(projectDO, result, null);
        }
        return result;
    }

    public static ProjectDO toProjectDO(Project project) {
        ProjectDO result = null;
        if (project != null) {
            result = new ProjectDO();
            toProjectDOCopier.copy(project, result, null);
        }
        return result;
    }

    public static ProjectItem toProjectItem(ProjectItemDO projectItemDO) {
        ProjectItem result = null;
        if (projectItemDO != null) {
            result = new ProjectItem();
            toProjectItemCopier.copy(projectItemDO, result, null);
        }
        return result;
    }

    public static ProjectItemDO toProjectItemDO(ProjectItem projectItem, int projectId) {
        ProjectItemDO result = null;
        if (projectItem != null) {
            result = new ProjectItemDO();
            toProjectItemDOCopier.copy(projectItem, result, null);
            result.setProjectId(projectId);
        }
        return result;
    }

    public static ProjectContact toProjectContact(ProjectContactDO projectContactDO) {
        ProjectContact result = null;
        if (projectContactDO != null) {
            result = new ProjectContact();
            toProjectContactCopier.copy(projectContactDO, result, null);
        }
        return result;
    }

    public static ProjectContactDO toProjectContactDO(ProjectContact projectContact, int projectId) {
        ProjectContactDO result = null;
        if (projectContact != null) {
            result = new ProjectContactDO();
            toProjectContactDOCopier.copy(projectContact, result, null);
            result.setProjectId(projectId);
        }
        return result;
    }
}
