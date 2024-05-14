package com.demo.springjwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Candidat;

public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    // Basic CRUD operations provided by JpaRepository, including findById
    Optional<Candidat> findById(Long id);
    
    // Custom method to find Candidats by Entretien
    List<Candidat> findByEntretienId(Long entretienId);
    
    // Custom method to find Candidats by Candidature
    List<Candidat> findByCandidatureId(Long candidatureId);
}
