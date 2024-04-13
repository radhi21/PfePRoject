package com.demo.springjwt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.models.Candidature;
import com.demo.springjwt.repository.CandidatureRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CandidatureController {

    @Autowired
    private CandidatureRepository candidatureRepository;

    @GetMapping("/candidatures")
    public ResponseEntity<List<Candidature>> getAllCandidatures(@RequestParam(required = false) String sujet) {
        try {
            List<Candidature> candidatures = new ArrayList<>();

            if (sujet == null)
                candidatureRepository.findAll().forEach(candidatures::add);
            else
                candidatureRepository.findBySujetContainingIgnoreCase(sujet).forEach(candidatures::add);

            if (candidatures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(candidatures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/candidatures/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable("id") long id) {
        Optional<Candidature> candidatureData = candidatureRepository.findById(id);

        if (candidatureData.isPresent()) {
            return new ResponseEntity<>(candidatureData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/candidatures")
    public ResponseEntity<Candidature> createCandidature(@RequestBody Candidature candidature) {
        try {
            Candidature _candidature = candidatureRepository.save(candidature);
            return new ResponseEntity<>(_candidature, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/candidatures/{id}")
    public ResponseEntity<Candidature> updateCandidature(@PathVariable("id") long id, @RequestBody Candidature candidature) {
        Optional<Candidature> candidatureData = candidatureRepository.findById(id);

        if (candidatureData.isPresent()) {
            Candidature _candidature = candidatureData.get();
            _candidature.setStagier(candidature.getStagier());
            _candidature.setSujet(candidature.getSujet());
            _candidature.setResponsable(candidature.getResponsable());
            _candidature.setStatut(candidature.getStatut());
            return new ResponseEntity<>(candidatureRepository.save(_candidature), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/candidatures/{id}")
    public ResponseEntity<HttpStatus> deleteCandidature(@PathVariable("id") long id) {
        try {
            candidatureRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @DeleteMapping("/candidatures")
    public ResponseEntity<HttpStatus> deleteAllCandidatures() {
        try {
            candidatureRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @GetMapping("/candidatures/statut/{statut}")
    public ResponseEntity<List<Candidature>> findByStatut(@PathVariable("statut") String statut) {
        try {
            List<Candidature> candidatures = candidatureRepository.findByStatut(statut);

            if (candidatures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(candidatures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 
