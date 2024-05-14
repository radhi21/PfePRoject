package com.demo.springjwt.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "candidatures") 
public class Candidature {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "stagier")
    private String stagier;
    
    @Column(name = "responsable")
    private String responsable;
    
    @Column(name = "statut")
    private String statut; 
        
    @ManyToMany(mappedBy = "candidatures")
    private List<Entretien> entretiens = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "sujet_id") // Nom de la colonne de clé étrangère dans la table candidatures
    private Sujet sujet; // Modifier le nom de la variable pour respecter la convention de nommage

    public Candidature() {
    }

    public Long getId() {
        return id; 
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStagier() {
        return stagier;
    }

    public void setStagier(String stagier) {
        this.stagier = stagier;
    }

    public String getResponsable() {
        return responsable;
    } 

    public void setResponsable(String responsable) {
        this.responsable = responsable; 
    }

    public String getStatut() {
        return statut;
    } 
 
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Sujet getSujet() {
        return sujet;
    }   
    
    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
    }
}
