package com.ssm.demo.entity;

import java.util.Date;

public class Attachment {
    private Integer id;

    private String filename;

    private String filenameOriginal;

    private String mime;

    private Date createdAt;

    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilenameOriginal() {
        return filenameOriginal;
    }

    public void setFilenameOriginal(String filenameOriginal) {
        this.filenameOriginal = filenameOriginal;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}