package com.km.assignment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salesDoc")
public class Sales_Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private Date dateTime;

    @Column(name = "file_type")
    private  String fileType;

    @Column(name = "file_name")
    private String salesDocumentFileName;

    @Column(name = "file_size")
    private long fileSize;
    @Column(name="sales_person")
    private String salesPerson;

    @Column(name = "file")
    @Lob
    private byte[] salesDocumentFile;

    public Sales_Document(){

    }

    public Sales_Document(Date dateTime, String fileType, String salesDocumentFileName,Long fileSize, String salesPerson, byte[] salesDocumentFile) {
        this.dateTime = dateTime;
        this.fileType = fileType;
        this.salesDocumentFileName = salesDocumentFileName;
        this.fileSize = fileSize;
        this.salesPerson = salesPerson;
        this.salesDocumentFile = salesDocumentFile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSalesDocumentFileName() {
        return salesDocumentFileName;
    }

    public void setSalesDocumentFileName(String salesDocumentFileName) {
        this.salesDocumentFileName = salesDocumentFileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public byte[] getSalesDocumentFile() {
        return salesDocumentFile;
    }

    public void setSalesDocumentFile(byte[] salesDocumentFile) {
        this.salesDocumentFile = salesDocumentFile;
    }

}
