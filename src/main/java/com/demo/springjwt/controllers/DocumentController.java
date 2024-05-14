package com.demo.springjwt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.models.Document;
import com.demo.springjwt.repository.DocumentRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/documents")
    public ResponseEntity<List<Document>> getAllDocuments() {
        try {
            List<Document> documents = new ArrayList<>();
            documentRepository.findAll().forEach(documents::add);

            if (documents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable("id") long id) {
        Optional<Document> documentData = documentRepository.findById(id);

        if (documentData.isPresent()) {
            return new ResponseEntity<>(documentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }    
  
    @PostMapping("/documents")
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        try {
            // Accéder à l'ID du stagiaire associé au document
            Long stagierId = document.getStagier().getId();
            
            // Afficher l'ID du stagiaire associé au document dans la console
            System.out.println("ID du stagiaire associé au document : " + stagierId); 
            
            Document _document = documentRepository.save(document);
            return new ResponseEntity<>(_document, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/documents/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable("id") long id, @RequestBody Document document) {
        Optional<Document> documentData = documentRepository.findById(id);

        if (documentData.isPresent()) {
            Document _document = documentData.get();
            _document.setNomDocument(document.getNomDocument());
            return new ResponseEntity<>(documentRepository.save(_document), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable("id") long id) {
        try {
            documentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/documents")
    public ResponseEntity<HttpStatus> deleteAllDocuments() {
        try {
            documentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
