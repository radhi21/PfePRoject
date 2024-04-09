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

import com.demo.springjwt.models.Offre;
import com.demo.springjwt.repository.OffreRepository;


@CrossOrigin(origins = "http://localhost:'4200")
@RestController
@RequestMapping("/api")
public class OffreControllers {

	@Autowired
	private OffreRepository OffreRepository;

	@GetMapping("/offres")
	public ResponseEntity<List<Offre>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<Offre>Offre = new ArrayList<Offre>();

			if (title == null)
				OffreRepository.findAll().forEach(Offre::add);
			else
				OffreRepository.findByTitleContainingIgnoreCase(title).forEach(Offre::add);

			if (Offre.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Offre, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/offres/{id}")
	public ResponseEntity<Offre> getTutorialById(@PathVariable("id") long id) {
		Optional<Offre> tutorialData = OffreRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/offres")
	public ResponseEntity<Offre> createTutorial(@RequestBody Offre offre) {
		try {
			Offre _offre = OffreRepository
					.save(offre);
			return new ResponseEntity<>(_offre, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/offres/{id}")
	public ResponseEntity<Offre> updateTutorial(@PathVariable("id") long id, @RequestBody Offre offre) {
		Optional<Offre> offreData = OffreRepository.findById(id);

		if (offreData.isPresent()) {
			Offre _offre = offreData.get();
			_offre.setTitle(offre.getTitle());
			_offre.setDescription(offre.getDescription());
			_offre.setPublished(offre.isPublished());
			return new ResponseEntity<>(OffreRepository.save(_offre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/offres/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			OffreRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/offres")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			OffreRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/offres/published")
	public ResponseEntity<List<Offre>> findByPublished() {
		try {
			List<Offre> Offres = OffreRepository.findByPublished(true);

			if (Offres.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(Offres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}