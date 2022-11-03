package com.km.assignment.payload;

import java.util.Date;

public class ResponseAllFiles {

    private long id;
    private Date date;
    private String fileName;

    private String category;

    private String description;
    private String salesPerson;
    private String fileType;
    private long size;

    public ResponseAllFiles(){

    }

    public ResponseAllFiles(long id, Date date, String fileName, String category, String description, String salesPerson, String fileType, long size) {
        this.id = id;
        this.date = date;
        this.fileName = fileName;
        this.category = category;
        this.description = description;
        this.salesPerson = salesPerson;
        this.fileType = fileType;
        this.size = size;
    }

    public Date getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
