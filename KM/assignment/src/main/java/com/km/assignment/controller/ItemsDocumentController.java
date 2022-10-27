package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.Items_Document;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.ItemsDocumentRepository;
import com.km.assignment.service.ItemsDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items/files/")
public class ItemsDocumentController {

    @Autowired
    private ItemsDocumentRepository itemsDocumentRepository;

    @Autowired
    private ItemsDocumentFileService itemsDocumentFileService;

    //get all item documents
    @GetMapping("/itemFilesList")
    public List<ResponseAllFiles> getAllItemDocuments(){
        return itemsDocumentFileService.getItemAllFileDocuments();
    }


    //upload files
    @PostMapping("/uploadItems")
    public Response uploadItemsFile(@RequestParam("file") MultipartFile file, @RequestParam("category") String category, @RequestParam("description") String description, @RequestParam("author") String author) {
        Items_Document itemsDocument = itemsDocumentFileService.storeFile(file, category, description, author);

        String salesFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(itemsDocument.getItemsDocumentFileName())
                .toUriString();

        return new Response(itemsDocument.getItemsDocumentFileName(),itemsDocument.getCategory(),itemsDocument.getDescription(),salesFileDownloadUri,file.getContentType(),file.getSize());
    }

    //get file by id
    @GetMapping("/itemFile/{fileId}")
    public ResponseEntity<Items_Document> getItemsFileById(@PathVariable Long fileId){
        Items_Document itemsDocument = itemsDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Items File " + fileId +" does not exist."));

        return ResponseEntity.ok(itemsDocument);
    }

    //delete files
    @DeleteMapping("/itemFile/{fileId}")
    public ResponseEntity<Map<String,Boolean>> deleteItemFiles(@PathVariable long fileId){
        Items_Document itemsDocument = itemsDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Items File " + fileId +" does not exist."));

        itemsDocumentRepository.delete(itemsDocument);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
