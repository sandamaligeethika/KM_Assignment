package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.EmployeeDocument;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.EmployeeDocumentRepository;
import com.km.assignment.service.EmployeeDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee/files/")
public class EmployeeDocumentController {

  @Autowired private EmployeeDocumentRepository employeeDocumentRepository;

  @Autowired private EmployeeDocumentFileService employeeDocumentFileService;

  // get all employee documents
  @GetMapping("/employeeFilesList")
  public List<ResponseAllFiles> getAllEmployeeDocuments() {
    return employeeDocumentFileService.getEmployeeAllFileDocuments();
  }

  // upload files
  @PostMapping(value = "/uploadEmployeeFiles", consumes = {"multipart/form-data"})
  public Response uploadEmployeeFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam("category") String category,
      @RequestParam("description") String description,
      @RequestParam("author") String author) {

    EmployeeDocument employeeDocument =
        employeeDocumentFileService.uploadEmployeeDocument(file, category, description, author);

    return new Response(
        employeeDocument.getEmployeeDocumentFileName(),
        category,
        description,
        employeeDocument.getUri(),
        employeeDocument.getFileType(),
        employeeDocument.getFileSize());
  }

  // get file by id
  @GetMapping("/employeeFile/{fileId}")
  public ResponseEntity<EmployeeDocument> getEmployeeFileById(@PathVariable Long fileId) {
    EmployeeDocument employeeDocument =
        employeeDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Items File " + fileId + " does not exist."));

    return ResponseEntity.ok(employeeDocument);
  }

  // update files
  @PatchMapping(value = "/employeeFile/{fileId}", consumes = {"*"})
  public ResponseEntity<EmployeeDocument> updateEmployeeFiles(
      @PathVariable Long fileId,
      @RequestParam("file") MultipartFile file,
      @RequestParam("category") String category,
      @RequestParam("description") String description,
      @RequestParam("author") String author) {

    EmployeeDocument updatedEmployeeFile =
        employeeDocumentFileService.updateEmployeeFiles(
            fileId, file, category, description, author);

    return ResponseEntity.ok(updatedEmployeeFile);
  }

  // delete files
  @DeleteMapping("/employeeFile/{fileId}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployeeFiles(@PathVariable long fileId) {
    EmployeeDocument employeeDocument =
        employeeDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Items File " + fileId + " does not exist."));

    employeeDocumentRepository.delete(employeeDocument);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
