package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.Employee_Document;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.EmployeeDocumentRepository;
import com.km.assignment.service.EmployeeDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee/files/")
public class EmployeeDocumentController {

    @Autowired
    private EmployeeDocumentRepository employeeDocumentRepository;

    @Autowired
    private EmployeeDocumentFileService employeeDocumentFileService;

    //get all employee documents
    @GetMapping("/employeeFilesList")
    public List<ResponseAllFiles> getAllEmployeeDocuments(){
        return employeeDocumentFileService.getEmployeeAllFileDocuments();
    }

    //upload files
    @PostMapping("/upload")
    public Response uploadEmployeeFile(@RequestParam("file") MultipartFile file, @RequestParam("category") String category,@RequestParam("description") String description, @RequestParam("author") String author) {
        Employee_Document employeeDocument = employeeDocumentFileService.storeFile(file, category, description, author);

        String salesFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(employeeDocument.getEmployeeDocumentFileName())
                .toUriString();

        return new Response(employeeDocument.getEmployeeDocumentFileName(),employeeDocument.getCategory(),employeeDocument.getDescription(),salesFileDownloadUri,file.getContentType(),file.getSize());
    }

    //get file by id
    @GetMapping("/employeeFile/{fileId}")
    public ResponseEntity<Employee_Document> getEmployeeFileById(@PathVariable Long fileId){
        Employee_Document employeeDocument = employeeDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Items File " + fileId +" does not exist."));

        return ResponseEntity.ok(employeeDocument);
    }

    //delete files
    @DeleteMapping("/employeeFile/{fileId}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployeeFiles(@PathVariable long fileId){
        Employee_Document employeeDocument = employeeDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Items File " + fileId +" does not exist."));

        employeeDocumentRepository.delete(employeeDocument);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
