package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Stagier;

public interface StagierRepository extends JpaRepository<Stagier, Long> {
    List<Stagier> findByNiveauEtude(String niveauEtude);
}
   