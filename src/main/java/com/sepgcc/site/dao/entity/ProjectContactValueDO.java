package com.sepgcc.site.dao.entity;

public class ProjectContactValueDO {

    private int id;
    private int projectId;
    private int projectContactId;
    private int uploadId;
    private String contactValue;

    public ProjectContactValueDO() {
    }

    public ProjectContactValueDO(int projectId, int projectContactId, int uploadId, String contactValue) {
        this.projectId = projectId;
        this.projectContactId = projectContactId;
        this.uploadId = uploadId;
        this.contactValue = contactValue;
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

    public int getProjectContactId() {
        return projectContactId;
    }

    public void setProjectContactId(int projectContactId) {
        this.projectContactId = projectContactId;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }
}
