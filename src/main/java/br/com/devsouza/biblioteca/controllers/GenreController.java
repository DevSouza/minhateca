package br.com.devsouza.biblioteca.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devsouza.biblioteca.dtos.GenreDto;
import br.com.devsouza.biblioteca.entities.GenreEntity;
import br.com.devsouza.biblioteca.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
	
	private final GenreRepository genreRepository;

	@GetMapping
	public ResponseEntity<?> getAllGenres() {
		log.debug("Get All Genres");
		
		return ResponseEntity.ok(genreRepository.findAll());
	}
	
	@GetMapping("/{genreId}")
	public ResponseEntity<?> getGenreById(@PathVariable UUID genreId) {
		log.debug("Get Genre by id: {}", genreId);
		
		Optional<GenreEntity> genreOptional = genreRepository.findById(genreId);
		if(!genreOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(genreOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> insertGenre(@RequestBody @Valid GenreDto genreDto) {
		log.debug("Insert Genre: {}", genreDto.getName());
		
		Optional<GenreEntity> genreOptional = genreRepository.findByName(genreDto.getName());
		if(genreOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Genero já cadastrado");
		
		var genre = new GenreEntity();
		BeanUtils.copyProperties(genreDto, genre);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(genreRepository.save(genre));
	}
	
	@PutMapping("/{genreId}")
	public ResponseEntity<?> updateGenreById(@PathVariable UUID genreId, @RequestBody @Valid GenreDto genreDto) {
		log.debug("Update Genre: {}", genreDto.getName());
		
		Optional<GenreEntity> genreOptional = genreRepository.findById(genreId);
		if(!genreOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		Optional<GenreEntity> genreByNameOptional = genreRepository.findByName(genreDto.getName());
		if(genreByNameOptional.isPresent() && !genreByNameOptional.get().getGenreId().equals(genreId)) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Genero já cadastrado");
		
		var genre = genreOptional.get();
		BeanUtils.copyProperties(genreDto, genre);
		
		return ResponseEntity.ok(genreRepository.save(genre));
	}
	
	@DeleteMapping("/{genreId}")
	public ResponseEntity<?> deleteGenreById(@PathVariable UUID genreId) {
		Optional<GenreEntity> genreOptional = genreRepository.findById(genreId);
		if(!genreOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		genreRepository.delete(genreOptional.get());
		
		return ResponseEntity.ok().build();
	}
	
}
