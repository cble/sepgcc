package com.sepgcc.site.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sepgcc.site.dao.ProjectAttachmentDAO;
import com.sepgcc.site.dao.ProjectContactDAO;
import com.sepgcc.site.dao.ProjectDAO;
import com.sepgcc.site.dao.ProjectItemDAO;
import com.sepgcc.site.dao.entity.ProjectAttachmentDO;
import com.sepgcc.site.dao.entity.ProjectContactDO;
import com.sepgcc.site.dao.entity.ProjectDO;
import com.sepgcc.site.dao.entity.ProjectItemDO;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.utils.ProjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private static final Logger log = Logger.getLogger(ProjectService.class);

    @Resource
    private ProjectDAO projectDAO;
    @Resource
    private ProjectItemDAO projectItemDAO;
    @Resource
    private ProjectContactDAO projectContactDAO;
    @Resource
    private ProjectAttachmentDAO projectAttachmentDAO;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private FileService fileService;

    public Project loadById(int projectId, List<Integer> status) {
        ProjectDO projectDO = projectDAO.loadById(projectId, status);
        Project project = ProjectUtils.toProject(projectDO);
        if (project != null) {
            project.setProjectItemList(queryProjectItemList(projectId));
            project.setProjectContactList(queryProjectContactList(projectId));
            project.setProjectAttachmentList(queryProjectAttachmentList(projectId));
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

    public List<ProjectAttachment> queryProjectAttachmentList(int projectId) {
        List<ProjectAttachmentDO> projectAttachmentDOs = projectAttachmentDAO.queryByProjectId(projectId);
        return Lists.transform(projectAttachmentDOs, new Function<ProjectAttachmentDO, ProjectAttachment>() {
            @Override
            public ProjectAttachment apply(ProjectAttachmentDO projectAttachmentDO) {
                ProjectAttachment projectAttachment = ProjectUtils.toProjectAttachment(projectAttachmentDO);
                if (projectAttachment != null) {
                    FileMeta file = fileService.getFile(projectAttachment.getFileId(), 0, false);
                    projectAttachment.setName(file.getFileName());
                }
                return projectAttachment;
            }
        });
    }

    public List<Project> queryWithLimit(int index, int limit, List<Integer> status) {
        List<Project> resultList = new ArrayList<Project>();
        List<ProjectDO> projectDOs = projectDAO.queryWithLimit(status, index, limit);
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

    public int countAll(List<Integer> status) {
        return projectDAO.countAll(status);
    }

    public int createProject(final Project project) {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int projectId = projectDAO.insert(ProjectUtils.toProjectDO(project));
                    bindContact(projectId, project.getProjectContactList());
                    bindItem(projectId, project.getProjectItemList());
                    bindAttachment(projectId, project.getProjectAttachmentList());
                    return projectId;
                }
            });
        } catch (IllegalArgumentException e) {
            log.warn(String.format("upload fail: %s", e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error("upload error", e);
            throw new IllegalArgumentException("未知错误");
        }
    }

    private void bindContact(int projectId, List<ProjectContact> projectContactList) {
        if (projectContactList == null) {
            return;
        }
        for (ProjectContact projectContact : projectContactList) {
            ProjectContactDO projectContactDO = ProjectUtils.toProjectContactDO(projectContact, projectId);
            projectContactDAO.insert(projectContactDO);
        }
    }

    private void bindItem(int projectId, List<ProjectItem> projectItemList) {
        if (projectItemList == null) {
            return;
        }
        for (ProjectItem projectItem : projectItemList) {
            ProjectItemDO itemDO = ProjectUtils.toProjectItemDO(projectItem, projectId);
            projectItemDAO.insert(itemDO);
        }
    }

    private void bindAttachment(int projectId, List<ProjectAttachment> attachmentList) {
        if (attachmentList == null) {
            return;
        }
        for (ProjectAttachment attachment : attachmentList) {
            ProjectAttachmentDO projectAttachmentDO = ProjectUtils.toProjectAttachmentDO(attachment, projectId);
            projectAttachmentDAO.insert(projectAttachmentDO);
        }
    }
}
