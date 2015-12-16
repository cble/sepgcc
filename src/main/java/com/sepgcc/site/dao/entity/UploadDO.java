package com.sepgcc.site.dao.entity;

public class UploadDO {

    private int id;
    private int projectId;
    private int userId;

    public UploadDO() {

    }

    public UploadDO(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
