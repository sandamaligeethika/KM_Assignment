package com.km.assignment.repository;

import com.km.assignment.model.Sales_Document;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesDocumentRepository extends JpaRepository<Sales_Document,Long> {

    @Override
    List<Sales_Document> findAll();
/*
    @Query("SELECT s FROM sales_doc s WHERE s.file_name LIKE %file%")
    Optional<List<Sales_Document>> findByFileLike(@Param("file") String file);*/

}
