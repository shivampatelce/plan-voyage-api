package com.example.plan_voyage.dto;

import jakarta.persistence.UniqueConstraint;

import java.util.Date;

public class DocumentDetailsDto {

    private String fileName;

    private String fileType;

    private long fileSize;

    private String uploaderFullName;

    private String uploaderId;

    private Date uploadDate;

    public DocumentDetailsDto(String fileName, String fileType, long fileSize, String uploaderFullName, String uploaderId, Date uploadDate) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploaderFullName = uploaderFullName;
        this.uploaderId = uploaderId;
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploaderFullName() {
        return uploaderFullName;
    }

    public void setUploaderFullName(String uploaderFullName) {
        this.uploaderFullName = uploaderFullName;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
