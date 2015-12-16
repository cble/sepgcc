package com.sepgcc.site.dao.entity;

public class ProjectFileDO {

    private int id;
    private int projectId;
    private int projectItemId;
    private int uploadId;
    private int fileId;

    public ProjectFileDO() {
    }

    public ProjectFileDO(int projectId, int projectItemId, int uploadId, int fileId) {
        this.projectId = projectId;
        this.projectItemId = projectItemId;
        this.uploadId = uploadId;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectItemId() {
        return projectItemId;
    }

    public void setProjectItemId(int projectItemId) {
        this.projectItemId = projectItemId;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
