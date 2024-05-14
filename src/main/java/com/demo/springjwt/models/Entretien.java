package com.demo.springjwt.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.Table;
 
@Entity  
@Table(name = "entretiens") 
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date") 
    private String date;
    
    @Column(name = "resultat")
    private String resultat;
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "candidat",
            joinColumns = @JoinColumn(name = "candidature_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "entretien_id", referencedColumnName = "id")
    ) // Fermeture de la parenthèse manquante
    private List<Candidature> candidatures = new ArrayList<>();
    
    public Entretien() {
    }

    public Long getId() {
        return id; 
    }
 
    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    } 

    public void setDate(String date) {
        this.date = date;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
}
