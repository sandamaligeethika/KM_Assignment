package com.km.assignment.repository;

import com.km.assignment.model.Employee_Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDocumentRepository extends JpaRepository<Employee_Document,Long> {

    @Override
    List<Employee_Document> findAll();
}