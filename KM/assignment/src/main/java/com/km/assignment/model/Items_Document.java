package com.km.assignment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "itemsDoc")
public class Items_Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private Date dateTime;

    @Column(name = "item_file_type")
    private  String fileType;

    @Column(name = "item_file_name")
    private String itemsDocumentFileName;

    @Column(name = "item_category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "item_file_size")
    private long fileSize;
    @Column(name="items_person")
    private String itemManagementPerson;

    @Column(name = "item_file")
    @Lob
    private byte[] itemsDocumentFile;

    public Items_Document(){

    }

    public Items_Document(Date dateTime, String fileType, String itemsDocumentFileName, String category, String description, long fileSize, String itemManagementPerson, byte[] itemsDocumentFile) {
        this.dateTime = dateTime;
        this.fileType = fileType;
        this.itemsDocumentFileName = itemsDocumentFileName;
        this.category = category;
        this.description = description;
        this.fileSize = fileSize;
        this.itemManagementPerson = itemManagementPerson;
        this.itemsDocumentFile = itemsDocumentFile;
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

    public String getItemsDocumentFileName() {
        return itemsDocumentFileName;
    }

    public void setItemsDocumentFileName(String itemsDocumentFileName) {
        this.itemsDocumentFileName = itemsDocumentFileName;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getItemManagementPerson() {
        return itemManagementPerson;
    }

    public void setItemManagementPerson(String itemManagementPerson) {
        this.itemManagementPerson = itemManagementPerson;
    }

    public byte[] getItemsDocumentFile() {
        return itemsDocumentFile;
    }

    public void setItemsDocumentFile(byte[] itemsDocumentFile) {
        this.itemsDocumentFile = itemsDocumentFile;
    }
}
