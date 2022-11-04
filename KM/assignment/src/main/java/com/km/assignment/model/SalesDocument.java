package com.km.assignment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salesDoc")
public class SalesDocument {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "date")
  private Date date;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "sales_category")
  private String category;

  @Column(name = "description")
  private String description;

  @Column(name = "file_size")
  private long fileSize;

  @Column(name = "sales_person")
  private String salesPerson;

  @Column(name = "file")
  private String uri;

  public SalesDocument() {}

  public SalesDocument(
      Date date,
      String fileType,
      String fileName,
      String category,
      String description,
      long fileSize,
      String salesPerson,
      String uri) {
    this.date = date;
    this.fileType = fileType;
    this.fileName = fileName;
    this.category = category;
    this.description = description;
    this.fileSize = fileSize;
    this.salesPerson = salesPerson;
    this.uri = uri;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date dateTime) {
    this.date = dateTime;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String salesDocumentFileName) {
    this.fileName = salesDocumentFileName;
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

  public String getSalesPerson() {
    return salesPerson;
  }

  public void setSalesPerson(String salesPerson) {
    this.salesPerson = salesPerson;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
