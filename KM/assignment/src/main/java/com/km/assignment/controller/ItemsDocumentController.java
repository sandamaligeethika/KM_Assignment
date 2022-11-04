package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.ItemsDocument;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.ItemsDocumentRepository;
import com.km.assignment.service.ItemsDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/items/files/")
public class ItemsDocumentController {

  @Autowired private ItemsDocumentRepository itemsDocumentRepository;

  @Autowired private ItemsDocumentFileService itemsDocumentFileService;

  // get all item documents
  @GetMapping("/itemFilesList")
  public List<ResponseAllFiles> getAllItemDocuments() {
    return itemsDocumentFileService.getItemAllFileDocuments();
  }

  // upload files
  @PostMapping(value = "/uploadItems", consumes = {"multipart/form-data"})
  public Response uploadItemsFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam("category") String category,
      @RequestParam("description") String description,
      @RequestParam("author") String author) {

    ItemsDocument itemsDocument =
        itemsDocumentFileService.uploadItemDocument(file, category, description, author);

    return new Response(
        itemsDocument.getFileName(),
        category,
        description,
        itemsDocument.getUri(),
        itemsDocument.getFileType(),
        itemsDocument.getFileSize());
  }

  // get file by id
  @GetMapping("/itemFile/{fileId}")
  public ResponseEntity<ItemsDocument> getItemsFileById(@PathVariable Long fileId) {
    ItemsDocument itemsDocument =
        itemsDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Items File " + fileId + " does not exist."));

    return ResponseEntity.ok(itemsDocument);
  }

  @PatchMapping(value = "/updateItemFile/{fileId}", consumes = {"*"})
  public ResponseEntity<ItemsDocument> updateItemFiles(
      @PathVariable Long fileId,
      @RequestParam("file") MultipartFile file,
      @RequestParam("category") String category,
      @RequestParam("description") String description,
      @RequestParam("author") String author) {

    ItemsDocument updatedItemFile =
        itemsDocumentFileService.updateItemFiles(fileId, file, category, description, author);

    return ResponseEntity.ok(updatedItemFile);
  }

  // delete files
  @DeleteMapping("/itemFile/{fileId}")
  public ResponseEntity<Map<String, Boolean>> deleteItemFiles(@PathVariable long fileId) {
    ItemsDocument itemsDocument =
        itemsDocumentRepository
            .findById(fileId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Items File " + fileId + " does not exist."));

    itemsDocumentRepository.delete(itemsDocument);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/file/name/{name}")
  public ResponseEntity<List<ItemsDocument>> getItemFileByName(@PathVariable String name) {

    List<ItemsDocument> itemsDocuments = itemsDocumentFileService.findItemFilesByName(name);
    return ResponseEntity.ok(itemsDocuments);
  }
}
