package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.SalesDocument;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.SalesDocumentRepository;
import com.km.assignment.service.SalesDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/sales/files/")
public class SalesDocumentController {

  @Autowired private SalesDocumentRepository salesDocumentRepository;

  @Autowired private SalesDocumentFileService salesDocumentFileService;

  // get all Sales Documents
  @GetMapping("/filesList")
  public List<ResponseAllFiles> getAllSalesDocuments() {
    return salesDocumentFileService.getAllFileDocuments();
  }

  // upload files
  @PostMapping("/upload")
  public Response uploadSalesFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam("category") String category,
      @RequestParam("description") String description,
      @RequestParam("author") String author) {

    SalesDocument salesDocument =
        salesDocumentFileService.uploadSalesDocument(file, category, description, author);

    return new Response(
        salesDocument.getSalesDocumentFileName(),
        category,
        description,
        salesDocument.getUri(),
        salesDocument.getFileType(),
        salesDocument.getFileSize());
  }

  // get file by id
  @GetMapping("/file/{fileId}")
  public ResponseEntity<SalesDocument> getSalesFileById(@PathVariable Long fileId) {
    SalesDocument salesDocument =
        salesDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Sales File " + fileId + " does not exist."));

    return ResponseEntity.ok(salesDocument);
  }

  /*
  @GetMapping("/file/{name}")
  public ResponseEntity<List<Sales_Document>> getFileByName(@PathVariable String name) {
      List<Sales_Document> salesDocuments =
              salesDocumentRepository
                      .findByFileLike(name)
                      .orElseThrow(
                              () -> new ResourceNotFoundException("Movies not found with name: " + name));

      return ResponseEntity.ok(salesDocuments);
  }*/

  // update files
  @PutMapping("/file/{fileId}")
  public ResponseEntity<SalesDocument> updateSalesFiles(
      @PathVariable Long fileId,
      @RequestParam("file") MultipartFile file,
      @RequestParam("category") String category,
      @RequestParam("description") String description,
      @RequestParam("author") String author) {

    SalesDocument updatedSalesFile =
        salesDocumentFileService.updateSalesFiles(fileId, file, category, description, author);

    return ResponseEntity.ok(updatedSalesFile);
  }

  // delete files
  @DeleteMapping("file/{fileId}")
  public ResponseEntity<Map<String, Boolean>> deleteSalesFiles(@PathVariable long fileId) {
    SalesDocument salesDocument =
        salesDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Sales File " + fileId + " does not exist."));

    salesDocumentRepository.delete(salesDocument);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
