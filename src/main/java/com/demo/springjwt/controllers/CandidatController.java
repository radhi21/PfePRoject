package com.demo.springjwt.controllers;

import com.demo.springjwt.models.Candidat;
import com.demo.springjwt.repository.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CandidatController {

    @Autowired 
    private CandidatRepository candidatRepository;

    @GetMapping("/candidats")
    public ResponseEntity<List<Candidat>> getAllCandidats() {
        List<Candidat> candidats = candidatRepository.findAll();
        if (candidats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(candidats, HttpStatus.OK);
    }

    @GetMapping("/candidats/{id}")
    public ResponseEntity<Candidat> getCandidatById(@PathVariable("id") long id) {
        Optional<Candidat> candidatData = candidatRepository.findById(id);
        return candidatData.map(candidat -> new ResponseEntity<>(candidat, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/candidats")
    public ResponseEntity<Candidat> createCandidat(@RequestBody Candidat candidat) {
        try {
            Candidat _candidat = candidatRepository.save(candidat);
            return new ResponseEntity<>(_candidat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/candidats/{id}")
    public ResponseEntity<Candidat> updateCandidat(@PathVariable("id") long id, @RequestBody Candidat candidat) {
        Optional<Candidat> candidatData = candidatRepository.findById(id);
        if (candidatData.isPresent()) {
            Candidat _candidat = candidatData.get();
            _candidat.setCandidature(candidat.getCandidature());
            _candidat.setEntretien(candidat.getEntretien());
            return new ResponseEntity<>(candidatRepository.save(_candidat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/candidats/{id}")
    public ResponseEntity<HttpStatus> deleteCandidat(@PathVariable("id") long id) {
        try {
            candidatRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
