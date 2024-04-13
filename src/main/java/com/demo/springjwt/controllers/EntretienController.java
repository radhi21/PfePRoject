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

import com.demo.springjwt.models.Entretien;
import com.demo.springjwt.repository.EntretienRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EntretienController {

    @Autowired
    private EntretienRepository entretienRepository;

    @GetMapping("/entretiens")
    public ResponseEntity<List<Entretien>> getAllEntretiens(@RequestParam(required = false) String date) {
        try {
            List<Entretien> entretiens = new ArrayList<>();

            if (date == null)
                entretienRepository.findAll().forEach(entretiens::add);
            else
                entretienRepository.findByDate(date).forEach(entretiens::add);

            if (entretiens.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(entretiens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entretiens/{id}")
    public ResponseEntity<Entretien> getEntretienById(@PathVariable("id") long id) {
        Optional<Entretien> entretienData = entretienRepository.findById(id);

        if (entretienData.isPresent()) {
            return new ResponseEntity<>(entretienData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/entretiens")
    public ResponseEntity<Entretien> createEntretien(@RequestBody Entretien entretien) {
        try {
            Entretien _entretien = entretienRepository.save(entretien);
            return new ResponseEntity<>(_entretien, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/entretiens/{id}")
    public ResponseEntity<Entretien> updateEntretien(@PathVariable("id") long id, @RequestBody Entretien entretien) {
        Optional<Entretien> entretienData = entretienRepository.findById(id);

        if (entretienData.isPresent()) {
            Entretien _entretien = entretienData.get();
            _entretien.setDate(entretien.getDate());
            _entretien.setResultat(entretien.getResultat());
            return new ResponseEntity<>(entretienRepository.save(_entretien), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/entretiens/{id}")
    public ResponseEntity<HttpStatus> deleteEntretien(@PathVariable("id") long id) {
        try {
            entretienRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/entretiens")
    public ResponseEntity<HttpStatus> deleteAllEntretiens() {
        try {
            entretienRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entretiens/date/{date}")
    public ResponseEntity<List<Entretien>> findByDate(@PathVariable("date") String date) {
        try {
            List<Entretien> entretiens = entretienRepository.findByDate(date);

            if (entretiens.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(entretiens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
