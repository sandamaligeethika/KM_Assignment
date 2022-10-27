package com.km.assignment.payload;

import java.util.Date;

public class ResponseAllFiles {

    private Date date;
    private String fileName;
    private String salesPerson;
    private String fileType;
    private long size;

    public ResponseAllFiles(){

    }

    public ResponseAllFiles(Date date, String fileName, String salesPerson, String fileType, long size) {
        this.date = date;
        this.fileName = fileName;
        this.salesPerson = salesPerson;
        this.fileType = fileType;
        this.size = size;
    }

    public Date getDate() {
        return date;
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
