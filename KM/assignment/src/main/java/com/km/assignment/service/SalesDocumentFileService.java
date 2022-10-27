package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.Sales_Document;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.SalesDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesDocumentFileService {

    @Autowired
    private SalesDocumentRepository salesDocumentRepository;

    public Sales_Document storeFile(MultipartFile file, String author){

        //Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            //check for invalid characters in the documents
            if(fileName.contains("..")){
                throw new DocumentStorageException("Sorry, " + fileName + " contains invalid characters");
            }

            Sales_Document salesDocument = new Sales_Document(new Date(), file.getContentType(), fileName, file.getSize(), author, file.getBytes());

            return salesDocumentRepository.save(salesDocument);
        } catch (IOException e) {
            throw new DocumentStorageException("Cannot store file "+ fileName + ". " +e);
        }
    }


    public Sales_Document getSalesDocument(Long fileId){
        return salesDocumentRepository.findById(fileId).orElseThrow(()->new SalesFileNotFoundException("File Not Found with id " + fileId));
    }

    public List<ResponseAllFiles> getAllFileDocuments(){
        List<Sales_Document> files = salesDocumentRepository.findAll();
        return convertEntityFilesToFiles(files);
    }

    private List<ResponseAllFiles> convertEntityFilesToFiles(List<Sales_Document> files) {
        List<ResponseAllFiles> allFiles = new ArrayList<>();
        for (Sales_Document document:files) {
            ResponseAllFiles responseAllFile = new ResponseAllFiles();
            responseAllFile.setDate(document.getDateTime());
            responseAllFile.setFileName(document.getSalesDocumentFileName());
            responseAllFile.setFileType(document.getFileType());
            responseAllFile.setSalesPerson(document.getSalesPerson());
            responseAllFile.setSize(document.getFileSize());

            allFiles.add(responseAllFile);
        }

        return allFiles;
    }
}
