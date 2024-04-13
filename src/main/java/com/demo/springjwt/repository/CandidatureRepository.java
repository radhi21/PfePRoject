package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Candidature;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByStatut(String statut);
    List<Candidature> findBySujetContainingIgnoreCase(String sujet);
}
