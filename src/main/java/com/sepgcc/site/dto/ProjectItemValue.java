package com.sepgcc.site.dto;

import java.util.List;

public class ProjectItemValue extends ProjectItem {

    private List<FileMeta> fileMetaList;

    public List<FileMeta> getFileMetaList() {
        return fileMetaList;
    }

    public void setFileMetaList(List<FileMeta> fileMetaList) {
        this.fileMetaList = fileMetaList;
    }
}
