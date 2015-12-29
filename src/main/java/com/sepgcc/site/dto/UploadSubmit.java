package com.sepgcc.site.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UploadSubmit implements Serializable {

    private Map<String, String> contacts;
    private Map<String, List<String>> items;
    private int uploadId;

    public Map<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public Map<String, List<String>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<String>> items) {
        this.items = items;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }
}
