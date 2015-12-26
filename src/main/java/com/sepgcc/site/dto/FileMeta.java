package com.sepgcc.site.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"id", "storagePath", "userId", "bytes"})
public class FileMeta implements Serializable {
    private int id;
    private String fileId;
    private String fileName;
    private String storagePath;
    private int userId;
    private String fileType;
    private byte[] bytes;

    public FileMeta() {
    }

    public FileMeta(String fileId) {
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
