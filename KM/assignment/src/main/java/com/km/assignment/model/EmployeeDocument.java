package com.km.assignment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employeeDoc")
public class EmployeeDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "date")
  private Date dateTime;

  @Column(name = "employee_file_type")
  private String fileType;

  @Column(name = "employee_file_name")
  private String employeeDocumentFileName;

  @Column(name = "employee_category")
  private String category;

  @Column(name = "description")
  private String description;

  @Column(name = "employee_file_size")
  private long fileSize;

  @Column(name = "employee_person")
  private String employeeManagementPerson;

  @Column(name = "employee_file")
  private String uri;

  public EmployeeDocument() {}

  public EmployeeDocument(
      Date dateTime,
      String fileType,
      String employeeDocumentFileName,
      String category,
      String description,
      long fileSize,
      String employeeManagementPerson,
      String uri) {
    this.dateTime = dateTime;
    this.fileType = fileType;
    this.employeeDocumentFileName = employeeDocumentFileName;
    this.category = category;
    this.description = description;
    this.fileSize = fileSize;
    this.employeeManagementPerson = employeeManagementPerson;
    this.uri = uri;
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

  public String getEmployeeDocumentFileName() {
    return employeeDocumentFileName;
  }

  public void setEmployeeDocumentFileName(String employeeDocumentFileName) {
    this.employeeDocumentFileName = employeeDocumentFileName;
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

  public String getEmployeeManagementPerson() {
    return employeeManagementPerson;
  }

  public void setEmployeeManagementPerson(String employeeManagementPerson) {
    this.employeeManagementPerson = employeeManagementPerson;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
