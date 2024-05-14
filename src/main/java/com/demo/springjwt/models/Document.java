package com.demo.springjwt.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom_document")
    private String nomDocument; 
    
    @ManyToOne
    @JoinColumn(name = "id_stagier") // Colonne de clé étrangère qui lie le document au stagiaire
    private Stagier stagier;
    
    public Document() {
    } 

    // Getters and setters
    public Long getId() {
        return id; 
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDocument() {
        return nomDocument;
    }

    public void setNomDocument(String nomDocument) {
        this.nomDocument = nomDocument;
    }

    public Stagier getStagier() {
        return stagier;
    }

    public void setStagier(Stagier stagier) {
        this.stagier = stagier;
    }
}
