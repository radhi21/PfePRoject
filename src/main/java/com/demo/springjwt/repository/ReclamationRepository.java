package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByTitreReclamationContainingIgnoreCase(String titreReclamation);
    List<Reclamation> findByEtat(String etat);
}
