package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Sujet;

public interface SujetRepository extends JpaRepository<Sujet, Long> {
    List<Sujet> findByTitreContainingIgnoreCase(String titre);
    List<Sujet> findByNiveau(String niveau);
}
 