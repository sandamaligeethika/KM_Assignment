package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.Sales_Document;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.SalesDocumentRepository;
import com.km.assignment.service.SalesDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/sales/files/")
public class SalesDocumentController {

    @Autowired
    private SalesDocumentRepository salesDocumentRepository;

    @Autowired
    private SalesDocumentFileService salesDocumentFileService;

    //get all Sales Documents
    @GetMapping("/filesList")
    public List<ResponseAllFiles> getAllSalesDocuments(){
        return salesDocumentFileService.getAllFileDocuments();
    }

    //upload files
    @PostMapping("/upload")
    public Response uploadSalesFile(@RequestParam("file") MultipartFile file, @RequestParam("author") String author) {
        Sales_Document salesDocument = salesDocumentFileService.storeFile(file, author);

        String salesFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(salesDocument.getSalesDocumentFileName())
                .toUriString();

        return new Response(salesDocument.getSalesDocumentFileName(),salesFileDownloadUri,file.getContentType(),file.getSize());
    }

    //get file by id
    @GetMapping("/file/{fileId}")
    public ResponseEntity<Sales_Document> getSalesFileById(@PathVariable Long fileId){
        Sales_Document salesDocument = salesDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Sales File " + fileId +" does not exist."));

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

    //update files
    @PutMapping("/file/{fileId}")
    public ResponseEntity<Sales_Document> updateSalesFiles(@PathVariable Long fileId, @RequestBody Sales_Document salesFileDetails){
        Sales_Document salesDocument = salesDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Sales File " + fileId +" does not exist."));

        salesDocument.setDateTime(salesFileDetails.getDateTime());
        salesDocument.setFileType(salesFileDetails.getFileType());
        salesDocument.setSalesDocumentFileName(salesFileDetails.getSalesDocumentFileName());
        salesDocument.setFileSize(salesFileDetails.getFileSize());
        salesDocument.setSalesPerson(salesFileDetails.getSalesPerson());
        salesDocument.setSalesDocumentFile(salesFileDetails.getSalesDocumentFile());

        Sales_Document updatedSalesFile = salesDocumentRepository.save(salesDocument);

        return ResponseEntity.ok(updatedSalesFile);
    }
}
