package com.sepgcc.site.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {

    private int id;
    private String name;
    private String description;
    private Date beginTime;
    private Date endTime;
    private int successNumber;
    private String owner;
    private int status;
    private List<ProjectItem> projectItemList;
    private List<ProjectContact> projectContactList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProjectItem> getProjectItemList() {
        return projectItemList;
    }

    public void setProjectItemList(List<ProjectItem> projectItemList) {
        this.projectItemList = projectItemList;
    }

    public List<ProjectContact> getProjectContactList() {
        return projectContactList;
    }

    public void setProjectContactList(List<ProjectContact> projectContactList) {
        this.projectContactList = projectContactList;
    }
}
