package com.km.assignment.repository;

import com.km.assignment.model.Items_Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsDocumentRepository extends JpaRepository<Items_Document,Long> {

    @Override
    List<Items_Document> findAll();
}
