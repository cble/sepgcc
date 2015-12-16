package com.sepgcc.site.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UploadSubmit implements Serializable {


    private Map<Integer, List<String>> files;

    public Map<Integer, List<String>> getFiles() {
        return files;
    }

    public void setFiles(Map<Integer, List<String>> files) {
        this.files = files;
    }
}
