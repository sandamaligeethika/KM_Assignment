package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.ItemsDocument;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.ItemsDocumentRepository;
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
public class ItemsDocumentFileService {

  @Autowired private ItemsDocumentRepository itemsDocumentRepository;

  public ItemsDocument storeFile(
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

    ItemsDocument itemsDocument =
        new ItemsDocument(
            new Date(),
            file.getContentType(),
            fileName,
            category,
            description,
            file.getSize(),
            author,
            uri);

    return itemsDocumentRepository.save(itemsDocument);
  }

  public ItemsDocument getItemsDocument(Long fileId) {
    return itemsDocumentRepository
        .findById(fileId)
        .orElseThrow(() -> new SalesFileNotFoundException("File Not Found with id " + fileId));
  }

  public List<ResponseAllFiles> getItemAllFileDocuments() {
    List<ItemsDocument> itemFiles = itemsDocumentRepository.findAll();
    return convertEntityFilesToFiles(itemFiles);
  }

  private List<ResponseAllFiles> convertEntityFilesToFiles(List<ItemsDocument> files) {
    List<ResponseAllFiles> allItemFiles = new ArrayList<>();
    for (ItemsDocument itemDocument : files) {
      ResponseAllFiles responseAllFile = new ResponseAllFiles();
      responseAllFile.setDate(itemDocument.getDateTime());
      responseAllFile.setFileName(itemDocument.getItemsDocumentFileName());
      responseAllFile.setCategory(itemDocument.getCategory());
      responseAllFile.setDescription(itemDocument.getDescription());
      responseAllFile.setFileType(itemDocument.getFileType());
      responseAllFile.setSalesPerson(itemDocument.getItemManagementPerson());
      responseAllFile.setSize(itemDocument.getFileSize());

      allItemFiles.add(responseAllFile);
    }

    return allItemFiles;
  }

  public ItemsDocument updateItemFiles(
      Long fileId, MultipartFile file, String category, String description, String author) {
    String filePath = "C:/Users/sgeet/Desktop/Document_Store/" + file.getOriginalFilename();
    ItemsDocument itemsDocument =
        itemsDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Sales File " + fileId + " does not exist."));

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    itemsDocument.setId(fileId);
    itemsDocument.setDateTime(new Date());
    itemsDocument.setFileType(file.getContentType());
    itemsDocument.setItemsDocumentFileName(fileName);
    itemsDocument.setFileSize(file.getSize());
    itemsDocument.setCategory(category);
    itemsDocument.setDescription(description);
    itemsDocument.setItemManagementPerson(author);
    itemsDocument.setUri(filePath);

    ItemsDocument updatedItemFile = itemsDocumentRepository.save(itemsDocument);
    return updatedItemFile;
  }

  public ItemsDocument uploadItemDocument(
      MultipartFile file, String category, String description, String author) {
    String filePath = "C:/Users/sgeet/Desktop/Document_Store/" + file.getOriginalFilename();
    try {
      file.transferTo(new File(filePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ItemsDocument itemsDocument = storeFile(file, category, description, author, filePath);
    return itemsDocument;
  }
}
