package com.km.assignment.service;

import com.km.assignment.exception.DocumentStorageException;
import com.km.assignment.exception.SalesFileNotFoundException;
import com.km.assignment.model.Employee_Document;
import com.km.assignment.payload.ResponseAllFiles;
import com.km.assignment.repository.EmployeeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeDocumentFileService {

    @Autowired
    private EmployeeDocumentRepository employeeDocumentRepository;

    public Employee_Document storeFile(MultipartFile file, String category, String description, String author){

        //Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            //check for invalid characters in the documents
            if(fileName.contains("..")){
                throw new DocumentStorageException("Sorry, " + fileName + " contains invalid characters");
            }

            Employee_Document employeeDocument = new Employee_Document(new Date(), file.getContentType(), fileName, category, description, file.getSize(), author, file.getBytes());

            return employeeDocumentRepository.save(employeeDocument);
        } catch (IOException e) {
            throw new DocumentStorageException("Cannot store file "+ fileName + ". " +e);
        }
    }


    public Employee_Document getEmployeeDocument(Long fileId){
        return employeeDocumentRepository.findById(fileId).orElseThrow(()->new SalesFileNotFoundException("File Not Found with id " + fileId));
    }

    public List<ResponseAllFiles> getEmployeeAllFileDocuments(){
        List<Employee_Document> employeeFiles = employeeDocumentRepository.findAll();
        return convertEntityFilesToFiles(employeeFiles);
    }

    private List<ResponseAllFiles> convertEntityFilesToFiles(List<Employee_Document> files) {
        List<ResponseAllFiles> allEmployeeFiles = new ArrayList<>();
        for (Employee_Document employeeDocument:files) {
            ResponseAllFiles responseAllFile = new ResponseAllFiles();
            responseAllFile.setDate(employeeDocument.getDateTime());
            responseAllFile.setFileName(employeeDocument.getEmployeeDocumentFileName());
            responseAllFile.setCategory(employeeDocument.getCategory());
            responseAllFile.setDescription(employeeDocument.getDescription());
            responseAllFile.setFileType(employeeDocument.getFileType());
            responseAllFile.setSalesPerson(employeeDocument.getEmployeeManagementPerson());
            responseAllFile.setSize(employeeDocument.getFileSize());

            allEmployeeFiles.add(responseAllFile);
        }

        return allEmployeeFiles;
    }
}
