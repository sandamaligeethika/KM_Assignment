package com.km.assignment.controller;

import com.km.assignment.exception.ResourceNotFoundException;
import com.km.assignment.model.OrderDeliveryDocument;
import com.km.assignment.payload.Response;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.OrderDeliveryDocumentRepository;
import com.km.assignment.service.OrderDeliveryDocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderDelivery/files/")
public class OrderDeliveryDocumentController {

    @Autowired
    private OrderDeliveryDocumentRepository orderDeliveryDocumentRepository;

    @Autowired
    private OrderDeliveryDocumentFileService orderDeliveryDocumentFileService;

    //get all item documents
    @GetMapping("/orderDeliveryFilesList")
    public List<ResponseAllFiles> getAllOrderDeliveryDocuments(){
        return orderDeliveryDocumentFileService.getOrderDeliveryAllFileDocuments();
    }


    //upload files
    @PostMapping("/uploadOrderDeliveryFiles")
    public Response uploadOrderDeliveryFile(@RequestParam("file") MultipartFile file, @RequestParam("category") String category, @RequestParam("description") String description, @RequestParam("author") String author) {
        OrderDeliveryDocument orderDeliveryDocuments = orderDeliveryDocumentFileService.storeFile(file, category, description, author);

        String salesFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(orderDeliveryDocuments.getOrderDeliveryDocumentFileName())
                .toUriString();

        return new Response(orderDeliveryDocuments.getOrderDeliveryDocumentFileName(),orderDeliveryDocuments.getCategory(),orderDeliveryDocuments.getDescription(),salesFileDownloadUri,file.getContentType(),file.getSize());
    }

    //get file by id
    @GetMapping("/orderDeliveryFile/{fileId}")
    public ResponseEntity<OrderDeliveryDocument> getOrderDeliveryFileById(@PathVariable Long fileId){
        OrderDeliveryDocument orderDeliveryDocument = orderDeliveryDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Order Delivery File " + fileId +" does not exist."));

        return ResponseEntity.ok(orderDeliveryDocument);
    }

    //delete files
    @DeleteMapping("/orderDeliveryFile/{fileId}")
    public ResponseEntity<Map<String,Boolean>> deleteOrderDeliveryFiles(@PathVariable long fileId){
        OrderDeliveryDocument orderDeliveryDocument = orderDeliveryDocumentRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("Items File " + fileId +" does not exist."));

        orderDeliveryDocumentRepository.delete(orderDeliveryDocument);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
