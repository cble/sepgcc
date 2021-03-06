package com.sepgcc.site.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties({"contactValueList", "itemValueList"})
public class Upload implements Serializable {

    private int id;
    private int projectId;
    private Project project;
    private int userId;
    private List<ProjectContactValue> contactValueList;
    private List<ProjectItemValue> itemValueList;

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ProjectContactValue> getContactValueList() {
        return contactValueList;
    }

    public void setContactValueList(List<ProjectContactValue> contactValueList) {
        this.contactValueList = contactValueList;
    }

    public List<ProjectItemValue> getItemValueList() {
        return itemValueList;
    }

    public void setItemValueList(List<ProjectItemValue> itemValueList) {
        this.itemValueList = itemValueList;
    }
}
