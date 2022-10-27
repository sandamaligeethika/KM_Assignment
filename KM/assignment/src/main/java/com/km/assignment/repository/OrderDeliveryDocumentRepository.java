package com.km.assignment.repository;

import com.km.assignment.model.Items_Document;
import com.km.assignment.model.OrderDeliveryDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDeliveryDocumentRepository extends JpaRepository<OrderDeliveryDocument,Long> {

    @Override
    List<OrderDeliveryDocument> findAll();
}