package com.sepgcc.site.dto;

import com.sepgcc.site.utils.CustomJsonDateDeserializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {

    private int id;
    private String name;
    @JsonProperty
    private String description;
    private Date beginTime;
    private Date endTime;
    private int successNumber;
    private String owner;
    private int userGroup;
    private int status;
    @JsonProperty
    private List<ProjectItem> projectItemList;
    @JsonProperty
    private List<ProjectContact> projectContactList;
    @JsonProperty
    private List<ProjectAttachment> projectAttachmentList;
    private int uploadId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        Date now = new Date();
        return status == 1
                && now.after(beginTime)
                && now.before(endTime);
    }

    public String getBeginTimeStr() {
        return beginTime != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginTime) : null;
    }

    public String getEndTimeStr() {
        return endTime != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime) : null;
    }

    public int getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(int successNumber) {
        this.successNumber = successNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(int userGroup) {
        this.userGroup = userGroup;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnore
    public List<ProjectItem> getProjectItemList() {
        return projectItemList;
    }

    public void setProjectItemList(List<ProjectItem> projectItemList) {
        this.projectItemList = projectItemList;
    }

    @JsonIgnore
    public List<ProjectContact> getProjectContactList() {
        return projectContactList;
    }

    public void setProjectContactList(List<ProjectContact> projectContactList) {
        this.projectContactList = projectContactList;
    }

    @JsonIgnore
    public List<ProjectAttachment> getProjectAttachmentList() {
        return projectAttachmentList;
    }

    public void setProjectAttachmentList(List<ProjectAttachment> projectAttachmentList) {
        this.projectAttachmentList = projectAttachmentList;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }
}
