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

import com.demo.springjwt.models.Reclamation;
import com.demo.springjwt.repository.ReclamationRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ReclamationController {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @GetMapping("/reclamations")
    public ResponseEntity<List<Reclamation>> getAllReclamations(@RequestParam(required = false) String titreReclamation) {
        try {
            List<Reclamation> reclamations = new ArrayList<>();

            if (titreReclamation == null)
                reclamationRepository.findAll().forEach(reclamations::add);
            else
                reclamationRepository.findByTitreReclamationContainingIgnoreCase(titreReclamation).forEach(reclamations::add);

            if (reclamations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reclamations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reclamations/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable("id") long id) {
        Optional<Reclamation> reclamationData = reclamationRepository.findById(id);

        if (reclamationData.isPresent()) {
            return new ResponseEntity<>(reclamationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reclamations")
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        try {
            Reclamation _reclamation = reclamationRepository.save(reclamation);
            return new ResponseEntity<>(_reclamation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reclamations/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable("id") long id, @RequestBody Reclamation reclamation) {
        Optional<Reclamation> reclamationData = reclamationRepository.findById(id);

        if (reclamationData.isPresent()) {
            Reclamation _reclamation = reclamationData.get();
            _reclamation.setTitreReclamation(reclamation.getTitreReclamation());
            _reclamation.setDescription(reclamation.getDescription());
            _reclamation.setEtat(reclamation.getEtat());
            return new ResponseEntity<>(reclamationRepository.save(_reclamation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } 

    @DeleteMapping("/reclamations/{id}")
    public ResponseEntity<HttpStatus> deleteReclamation(@PathVariable("id") long id) {
        try {
            reclamationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reclamations")
    public ResponseEntity<HttpStatus> deleteAllReclamations() {
        try {
            reclamationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  

    @GetMapping("/reclamations/etat/{etat}")
    public ResponseEntity<List<Reclamation>> findByEtat(@PathVariable("etat") String etat) {
        try {
            List<Reclamation> reclamations = reclamationRepository.findByEtat(etat);

            if (reclamations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reclamations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
