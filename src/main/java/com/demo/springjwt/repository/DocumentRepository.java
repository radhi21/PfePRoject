package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByNomDocument(String nomDocument);
}
