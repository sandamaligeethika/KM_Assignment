package com.km.assignment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderDelivery")
public class OrderDeliveryDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private Date dateTime;

    @Column(name = "file_type")
    private  String fileType;

    @Column(name = "file_name")
    private String orderDeliveryDocumentFileName;

    @Column(name = "order_delivery_category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name="order_delivery_person")
    private String orderDeliveryPerson;

    @Column(name = "file")
    @Lob
    private byte[] orderDeliveryDocumentFile;

    public OrderDeliveryDocument(){

    }

    public OrderDeliveryDocument(Date dateTime, String fileType, String orderDeliveryDocumentFileName, String category, String description, long fileSize, String orderDeliveryPerson, byte[] orderDeliveryDocumentFile) {
        this.dateTime = dateTime;
        this.fileType = fileType;
        this.orderDeliveryDocumentFileName = orderDeliveryDocumentFileName;
        this.category = category;
        this.description = description;
        this.fileSize = fileSize;
        this.orderDeliveryPerson = orderDeliveryPerson;
        this.orderDeliveryDocumentFile = orderDeliveryDocumentFile;
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

    public String getOrderDeliveryDocumentFileName() {
        return orderDeliveryDocumentFileName;
    }

    public void setOrderDeliveryDocumentFileName(String orderDeliveryDocumentFileName) {
        this.orderDeliveryDocumentFileName = orderDeliveryDocumentFileName;
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

    public String getOrderDeliveryPerson() {
        return orderDeliveryPerson;
    }

    public void setOrderDeliveryPerson(String orderDeliveryPerson) {
        this.orderDeliveryPerson = orderDeliveryPerson;
    }

    public byte[] getOrderDeliveryDocumentFile() {
        return orderDeliveryDocumentFile;
    }

    public void setOrderDeliveryDocumentFile(byte[] orderDeliveryDocumentFile) {
        this.orderDeliveryDocumentFile = orderDeliveryDocumentFile;
    }
}
