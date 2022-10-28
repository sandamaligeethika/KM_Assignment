package com.km.assignment.repository;

import com.km.assignment.model.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument,Long> {

    @Override
    List<EmployeeDocument> findAll();
}