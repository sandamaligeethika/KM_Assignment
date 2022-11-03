package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.EmployeeDocument;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.EmployeeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeDocumentFileService {

  @Autowired private EmployeeDocumentRepository employeeDocumentRepository;

  public EmployeeDocument storeFile(
      MultipartFile file, String category, String description, String author, String uri) {

    // Normalize file name
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      // check for invalid characters in the documents
      if (fileName.contains("..")) {
        throw new DocumentStorageException("Sorry, " + fileName + " contains invalid characters");
      }
    } catch (DocumentStorageException e) {
      throw new RuntimeException(e);
    }

    EmployeeDocument employeeDocument =
        new EmployeeDocument(
            new Date(),
            file.getContentType(),
            fileName,
            category,
            description,
            file.getSize(),
            author,
            uri);

    return employeeDocumentRepository.save(employeeDocument);
  }

  public EmployeeDocument getEmployeeDocument(Long fileId) {
    return employeeDocumentRepository
        .findById(fileId)
        .orElseThrow(() -> new SalesFileNotFoundException("File Not Found with id " + fileId));
  }

  public List<ResponseAllFiles> getEmployeeAllFileDocuments() {
    List<EmployeeDocument> employeeFiles = employeeDocumentRepository.findAll();
    return convertEntityFilesToFiles(employeeFiles);
  }

  private List<ResponseAllFiles> convertEntityFilesToFiles(List<EmployeeDocument> files) {
    List<ResponseAllFiles> allEmployeeFiles = new ArrayList<>();
    for (EmployeeDocument employeeDocument : files) {
      ResponseAllFiles responseAllFile = new ResponseAllFiles();
      responseAllFile.setId(employeeDocument.getId());
      responseAllFile.setDate(employeeDocument.getDateTime());
      responseAllFile.setFileName(employeeDocument.getEmployeeDocumentFileName());
      responseAllFile.setCategory(employeeDocument.getCategory());
      responseAllFile.setDescription(employeeDocument.getDescription());
      responseAllFile.setFileType(employeeDocument.getFileType());
      responseAllFile.setSalesPerson(employeeDocument.getEmployeeManagementPerson());
      responseAllFile.setSize(employeeDocument.getFileSize());

      allEmployeeFiles.add(responseAllFile);
    }

    return allEmployeeFiles;
  }

  public EmployeeDocument updateEmployeeFiles(
      Long fileId, MultipartFile file, String category, String description, String author) {
    String filePath = "C:\\Users\\himas\\OneDrive\\Desktop\\files\\" + file.getOriginalFilename();
    EmployeeDocument employeeDocument =
        employeeDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Sales File " + fileId + " does not exist."));

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    employeeDocument.setId(fileId);
    employeeDocument.setDateTime(new Date());
    employeeDocument.setFileType(file.getContentType());
    employeeDocument.setEmployeeDocumentFileName(fileName);
    employeeDocument.setFileSize(file.getSize());
    employeeDocument.setCategory(category);
    employeeDocument.setDescription(description);
    employeeDocument.setEmployeeManagementPerson(author);
    employeeDocument.setUri(filePath);

    EmployeeDocument updatedEmployeeFile = employeeDocumentRepository.save(employeeDocument);
    return updatedEmployeeFile;
  }

  public EmployeeDocument uploadEmployeeDocument(
      MultipartFile file, String category, String description, String author) {
    String filePath = "C:\\Users\\himas\\OneDrive\\Desktop\\files\\" + file.getOriginalFilename();
    try {
      file.transferTo(new File(filePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    EmployeeDocument employeeDocument = storeFile(file, category, description, author, filePath);
    return employeeDocument;
  }
}
