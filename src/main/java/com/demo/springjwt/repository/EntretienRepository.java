package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Entretien;

public interface EntretienRepository extends JpaRepository<Entretien, Long> {
    List<Entretien> findByDate(String date);
}
