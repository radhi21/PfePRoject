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

import com.demo.springjwt.models.Sujet;
import com.demo.springjwt.repository.SujetRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SujetController {

    @Autowired
    private SujetRepository sujetRepository;

    @GetMapping("/sujets")
    public ResponseEntity<List<Sujet>> getAllSujets(@RequestParam(required = false) String titre) {
        try {
            List<Sujet> sujets = new ArrayList<>();

            if (titre == null)
                sujetRepository.findAll().forEach(sujets::add);
            else
                sujetRepository.findByTitreContainingIgnoreCase(titre).forEach(sujets::add);

            if (sujets.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sujets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sujets/{id}")
    public ResponseEntity<Sujet> getSujetById(@PathVariable("id") long id) {
        Optional<Sujet> sujetData = sujetRepository.findById(id);

        if (sujetData.isPresent()) {
            return new ResponseEntity<>(sujetData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/sujets")
    public ResponseEntity<Sujet> createSujet(@RequestBody Sujet sujet) {
        try {
            
            Sujet _sujet = sujetRepository.save(sujet);
            return new ResponseEntity<>(_sujet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/sujets/{id}")
    public ResponseEntity<Sujet> updateSujet(@PathVariable("id") long id, @RequestBody Sujet sujet) {
        Optional<Sujet> sujetData = sujetRepository.findById(id);

        if (sujetData.isPresent()) {
            Sujet _sujet = sujetData.get();
            _sujet.setTitre(sujet.getTitre());
            _sujet.setDescription(sujet.getDescription());
            _sujet.setNiveau(sujet.getNiveau());
            return new ResponseEntity<>(sujetRepository.save(_sujet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } 

    @DeleteMapping("/sujets/{id}")
    public ResponseEntity<HttpStatus> deleteSujet(@PathVariable("id") long id) {
        try {
            sujetRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/sujets")
    public ResponseEntity<HttpStatus> deleteAllSujets() {
        try {
            sujetRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  

    @GetMapping("/sujets/niveau/{niveau}")
    public ResponseEntity<List<Sujet>> findByNiveau(@PathVariable("niveau") String niveau) {
        try {
            List<Sujet> sujets = sujetRepository.findByNiveau(niveau);

            if (sujets.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sujets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }   
}
