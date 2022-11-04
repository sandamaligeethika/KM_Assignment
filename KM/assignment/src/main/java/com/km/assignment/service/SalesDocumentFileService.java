package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.SalesDocument;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.SalesDocumentRepository;
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
public class SalesDocumentFileService {

  @Autowired private SalesDocumentRepository salesDocumentRepository;

  public SalesDocument storeFile(
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

    SalesDocument salesDocument =
        new SalesDocument(
            new Date(),
            file.getContentType(),
            fileName,
            category,
            description,
            file.getSize(),
            author,
            uri);

    return salesDocumentRepository.save(salesDocument);
  }

  public SalesDocument getSalesDocument(Long fileId) {
    return salesDocumentRepository
        .findById(fileId)
        .orElseThrow(() -> new SalesFileNotFoundException("File Not Found with id " + fileId));
  }

  public List<ResponseAllFiles> getAllFileDocuments() {
    List<SalesDocument> files = salesDocumentRepository.findAll();
    return convertEntityFilesToFiles(files);
  }

  private List<ResponseAllFiles> convertEntityFilesToFiles(List<SalesDocument> files) {
    List<ResponseAllFiles> allFiles = new ArrayList<>();
    for (SalesDocument document : files) {
      ResponseAllFiles responseAllFile = new ResponseAllFiles();
      responseAllFile.setId(document.getId());
      responseAllFile.setDate(document.getDate());
      responseAllFile.setFileName(document.getFileName());
      responseAllFile.setCategory(document.getCategory());
      responseAllFile.setDescription(document.getDescription());
      responseAllFile.setFileType(document.getFileType());
      responseAllFile.setSalesPerson(document.getSalesPerson());
      responseAllFile.setSize(document.getFileSize());

      allFiles.add(responseAllFile);
    }

    return allFiles;
  }

  public SalesDocument updateSalesFiles(
      Long fileId, MultipartFile file, String category, String description, String author) {
    String filePath = "C:/Users/sgeet/Desktop/Document_Store/" + file.getOriginalFilename();
    SalesDocument salesDocument =
        salesDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Sales File " + fileId + " does not exist."));

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    salesDocument.setId(fileId);
    salesDocument.setDate(new Date());
    salesDocument.setFileType(file.getContentType());
    salesDocument.setFileName(fileName);
    salesDocument.setFileSize(file.getSize());
    salesDocument.setCategory(category);
    salesDocument.setDescription(description);
    salesDocument.setSalesPerson(author);
    salesDocument.setUri(filePath);

    SalesDocument updatedSalesFile = salesDocumentRepository.save(salesDocument);
    return updatedSalesFile;
  }

  public SalesDocument uploadSalesDocument(
      MultipartFile file, String category, String description, String author) {
    String filePath = "C:\\Users\\himas\\OneDrive\\Desktop\\files\\" + file.getOriginalFilename();
    try {
      file.transferTo(new File(filePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    SalesDocument salesDocument = storeFile(file, category, description, author, filePath);
    return salesDocument;
  }

  public List<SalesDocument> findFilesByName(String name) {
    return salesDocumentRepository.findByFileNameContaining(name);
  }
}
