package com.km.assignment.repository;

import com.km.assignment.model.SalesDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesDocumentRepository extends JpaRepository<SalesDocument,Long> {

    @Override
    List<SalesDocument> findAll();

    List<SalesDocument> findByFileNameContaining(String name);
/*
    @Query("SELECT s FROM sales_doc s WHERE s.file_name LIKE %file%")
    Optional<List<Sales_Document>> findByFileLike(@Param("file") String file);*/

}
