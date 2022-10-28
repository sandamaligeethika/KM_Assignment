package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.OrderDeliveryDocument;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.OrderDeliveryDocumentRepository;
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
public class OrderDeliveryDocumentFileService {

  @Autowired private OrderDeliveryDocumentRepository orderDeliveryDocumentRepository;

  public OrderDeliveryDocument storeFile(
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

    OrderDeliveryDocument orderDeliveryDocument =
        new OrderDeliveryDocument(
            new Date(),
            file.getContentType(),
            fileName,
            category,
            description,
            file.getSize(),
            author,
            uri);

    return orderDeliveryDocumentRepository.save(orderDeliveryDocument);
  }

  public OrderDeliveryDocument getOrderDeliveryDocument(Long fileId) {
    return orderDeliveryDocumentRepository
        .findById(fileId)
        .orElseThrow(() -> new SalesFileNotFoundException("File Not Found with id " + fileId));
  }

  public List<ResponseAllFiles> getOrderDeliveryAllFileDocuments() {
    List<OrderDeliveryDocument> orderDeliveryFiles = orderDeliveryDocumentRepository.findAll();
    return convertEntityFilesToFiles(orderDeliveryFiles);
  }

  private List<ResponseAllFiles> convertEntityFilesToFiles(List<OrderDeliveryDocument> files) {
    List<ResponseAllFiles> allItemFiles = new ArrayList<>();
    for (OrderDeliveryDocument orderDeliveryDocument : files) {
      ResponseAllFiles responseAllFile = new ResponseAllFiles();
      responseAllFile.setDate(orderDeliveryDocument.getDateTime());
      responseAllFile.setFileName(orderDeliveryDocument.getOrderDeliveryDocumentFileName());
      responseAllFile.setCategory(orderDeliveryDocument.getCategory());
      responseAllFile.setDescription(orderDeliveryDocument.getDescription());
      responseAllFile.setFileType(orderDeliveryDocument.getFileType());
      responseAllFile.setSalesPerson(orderDeliveryDocument.getOrderDeliveryPerson());
      responseAllFile.setSize(orderDeliveryDocument.getFileSize());

      allItemFiles.add(responseAllFile);
    }

    return allItemFiles;
  }

  public OrderDeliveryDocument updateOrderDeliveryFiles(
      Long fileId, MultipartFile file, String category, String description, String author) {
    String filePath = "C:/Users/sgeet/Desktop/Document_Store/" + file.getOriginalFilename();
    OrderDeliveryDocument orderDeliveryDocument =
        orderDeliveryDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Sales File " + fileId + " does not exist."));

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    orderDeliveryDocument.setId(fileId);
    orderDeliveryDocument.setDateTime(new Date());
    orderDeliveryDocument.setFileType(file.getContentType());
    orderDeliveryDocument.setOrderDeliveryDocumentFileName(fileName);
    orderDeliveryDocument.setFileSize(file.getSize());
    orderDeliveryDocument.setCategory(category);
    orderDeliveryDocument.setDescription(description);
    orderDeliveryDocument.setOrderDeliveryPerson(author);
    orderDeliveryDocument.setUri(filePath);

    OrderDeliveryDocument updatedOrderDeliveryFile =
        orderDeliveryDocumentRepository.save(orderDeliveryDocument);
    return updatedOrderDeliveryFile;
  }

  public OrderDeliveryDocument uploadOrderDeliveryDocument(
      MultipartFile file, String category, String description, String author) {
    String filePath = "C:/Users/sgeet/Desktop/Document_Store/" + file.getOriginalFilename();
    try {
      file.transferTo(new File(filePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    OrderDeliveryDocument orderDeliveryDocument =
        storeFile(file, category, description, author, filePath);
    return orderDeliveryDocument;
  }
}
