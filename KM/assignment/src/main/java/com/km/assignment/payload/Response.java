package com.km.assignment.payload;

public class Response {

    private int id;
    private String fileName;

    private String category;

    private String description;
    private String fileUri;
    private String fileType;
    private long size;

    public Response( String fileName, String category, String description, String fileUri, String fileType, long size) {

        this.fileName = fileName;
        this.category = category;
        this.description = description;
        this.fileUri = fileUri;
        this.fileType = fileType;
        this.size = size;
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

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
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
