package com.sepgcc.site.utils;

import com.sepgcc.site.dao.entity.*;
import com.sepgcc.site.dto.*;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static ProjectContactValue toProjectContactValue(ProjectContactDO projectContactDO, ProjectContactValueDO value) {
        ProjectContactValue result = null;
        if (projectContactDO != null) {
            result = new ProjectContactValue();
            toProjectContactCopier.copy(projectContactDO, result, null);
            if (value != null) {
                result.setContactValue(value.getContactValue());
            }
        }
        return result;
    }

    private static ProjectItemValue toProjectItemValue(ProjectItemDO itemDO, List<FileMeta> fileMetaList) {
        ProjectItemValue result = null;
        if (itemDO != null) {
            result = new ProjectItemValue();
            toProjectItemCopier.copy(itemDO, result, null);
            result.setFileMetaList(fileMetaList);
        }
        return result;
    }

    public static List<ProjectContactValue> toProjectContactValueList(List<ProjectContactDO> contactList, List<ProjectContactValueDO> contactValueList) {
        Map<Integer, ProjectContactValueDO> contactValueMap = new HashMap<Integer, ProjectContactValueDO>();
        for (ProjectContactValueDO contactValueDO : contactValueList) {
            contactValueMap.put(contactValueDO.getProjectContactId(), contactValueDO);
        }

        List<ProjectContactValue> projectContactValueList = new ArrayList<ProjectContactValue>();
        for (ProjectContactDO contactDO : contactList) {
            projectContactValueList.add(toProjectContactValue(contactDO, contactValueMap.get(contactDO.getId())));
        }
        return projectContactValueList;
    }

    public static List<ProjectItemValue> toProjectItemValueList(List<ProjectItemDO> itemList, List<ProjectFileDO> projectFileList, Map<String, FileMeta> fileMetaMap) {
        Map<Integer, List<FileMeta>> fileMap = new HashMap<Integer, List<FileMeta>>();
        for (ProjectFileDO projectFileDO : projectFileList) {
            if (fileMap.get(projectFileDO.getProjectItemId()) == null) {
                fileMap.put(projectFileDO.getProjectItemId(), new ArrayList<FileMeta>());
            }
            CollectionUtils.addIgnoreNull(fileMap.get(projectFileDO.getProjectItemId()), fileMetaMap.get(projectFileDO.getFileId()));
        }

        List<ProjectItemValue> projectItemValueList = new ArrayList<ProjectItemValue>();
        for (ProjectItemDO itemDO : itemList) {
            projectItemValueList.add(toProjectItemValue(itemDO, fileMap.get(itemDO.getId())));
        }
        return projectItemValueList;
    }
}
