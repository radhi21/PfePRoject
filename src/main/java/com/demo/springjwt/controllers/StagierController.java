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

import com.demo.springjwt.models.Stagier;
import com.demo.springjwt.repository.StagierRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class StagierController {

    @Autowired
    private StagierRepository stagierRepository;

    @GetMapping("/stagiers")
    public ResponseEntity<List<Stagier>> getAllStagiers(@RequestParam(required = false) String niveauEtude) {
        try {
            List<Stagier> stagiers = new ArrayList<>();

            if (niveauEtude == null)
                stagierRepository.findAll().forEach(stagiers::add);
            else
                stagierRepository.findByNiveauEtude(niveauEtude).forEach(stagiers::add);

            if (stagiers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(stagiers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stagiers/{id}")
    public ResponseEntity<Stagier> getStagierById(@PathVariable("id") long id) {
        Optional<Stagier> stagierData = stagierRepository.findById(id);

        if (stagierData.isPresent()) {
            return new ResponseEntity<>(stagierData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/stagiers")
    public ResponseEntity<Stagier> createStagier(@RequestBody Stagier stagier) {
        try {
            // Accédez directement à l'ID de la candidature à partir de l'objet Candidature associé
            if (stagier.getCandidature() != null) {
                Long idCandidature = stagier.getCandidature().getId();
                System.out.println("ID de la candidature : " + idCandidature);
            }
               
            // Enregistrer le stagiaire dans la base de données
            Stagier _stagier = stagierRepository.save(stagier);
            
            // Retourner la réponse avec le stagiaire créé
            return new ResponseEntity<>(_stagier, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retourner une réponse d'erreur en cas d'échec de l'enregistrement
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 



    @PutMapping("/stagiers/{id}")
    public ResponseEntity<Stagier> updateStagier(@PathVariable("id") long id, @RequestBody Stagier stagier) {
        Optional<Stagier> stagierData = stagierRepository.findById(id);

        if (stagierData.isPresent()) {
            Stagier _stagier = stagierData.get();
            _stagier.setNiveauEtude(stagier.getNiveauEtude());
            _stagier.setDateNaissance(stagier.getDateNaissance());
            return new ResponseEntity<>(stagierRepository.save(_stagier), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/stagiers/{id}")
    public ResponseEntity<HttpStatus> deleteStagier(@PathVariable("id") long id) {
        try {
            stagierRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/stagiers")
    public ResponseEntity<HttpStatus> deleteAllStagiers() {
        try {
            stagierRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
