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
@Table(name = "reclamations")
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "titreReclamation")
    private String titreReclamation;

    @Column(name = "description")
    private String description;

    @Column(name = "etat")
    private String etat;
   
    @ManyToOne
    @JoinColumn(name = "id_candidature") // Nom de la colonne de clé étrangère dans la table reclamations
    private Candidature candidature;
     
    public Reclamation() {
    }

    // Getters et Setters pour id, titreReclamation, description, etat
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreReclamation() {
        return titreReclamation;
    }

    public void setTitreReclamation(String titreReclamation) {
        this.titreReclamation = titreReclamation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // Getter et Setter pour la relation Many-to-One avec Candidature
    public Candidature getCandidature() {
        return candidature;
    }

    public void setCandidature(Candidature candidature) {
        this.candidature = candidature;
    }
}
