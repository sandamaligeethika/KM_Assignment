package com.km.assignment.repository;

import com.km.assignment.model.ItemsDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsDocumentRepository extends JpaRepository<ItemsDocument,Long> {

    @Override
    List<ItemsDocument> findAll();
}
