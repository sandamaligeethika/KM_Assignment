package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.Items_Document;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.ItemsDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemsDocumentFileService {

    @Autowired
    private ItemsDocumentRepository itemsDocumentRepository;

    public Items_Document storeFile(MultipartFile file, String category, String description, String author){

        //Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            //check for invalid characters in the documents
            if(fileName.contains("..")){
                throw new DocumentStorageException("Sorry, " + fileName + " contains invalid characters");
            }

            Items_Document itemsDocument = new Items_Document(new Date(), file.getContentType(), fileName, category, description, file.getSize(), author, file.getBytes());

            return itemsDocumentRepository.save(itemsDocument);
        } catch (IOException e) {
            throw new DocumentStorageException("Cannot store file "+ fileName + ". " +e);
        }
    }


    public Items_Document getItemsDocument(Long fileId){
        return itemsDocumentRepository.findById(fileId).orElseThrow(()->new SalesFileNotFoundException("File Not Found with id " + fileId));
    }

    public List<ResponseAllFiles> getItemAllFileDocuments(){
        List<Items_Document> itemFiles = itemsDocumentRepository.findAll();
        return convertEntityFilesToFiles(itemFiles);
    }

    private List<ResponseAllFiles> convertEntityFilesToFiles(List<Items_Document> files) {
        List<ResponseAllFiles> allItemFiles = new ArrayList<>();
        for (Items_Document itemDocument:files) {
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
}
