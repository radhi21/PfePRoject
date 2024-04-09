package com.demo.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Offre;



public interface OffreRepository extends JpaRepository<Offre, Long> {
	List<Offre> findByPublished(boolean published);
  List<Offre> findByTitleContainingIgnoreCase(String title);
}
