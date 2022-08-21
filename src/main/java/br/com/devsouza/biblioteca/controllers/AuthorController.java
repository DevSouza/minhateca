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

import br.com.devsouza.biblioteca.dtos.AuthorDto;
import br.com.devsouza.biblioteca.entities.AuthorEntity;
import br.com.devsouza.biblioteca.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

	private final AuthorRepository authorRepository;

	@GetMapping
	public ResponseEntity<?> getAllAuthors() {
		log.debug("Get All Authors");
		
		return ResponseEntity.ok(authorRepository.findAll());
	}
	
	@GetMapping("/{authorId}")
	public ResponseEntity<?> getAuthorById(@PathVariable UUID authorId) {
		log.debug("Get Author by id: {}", authorId);
		
		Optional<AuthorEntity> authorOptional = authorRepository.findById(authorId);
		if(!authorOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(authorOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> insertAuthor(@RequestBody @Valid AuthorDto authorDto) {
		log.debug("Insert Author: {}", authorDto.getName());
		
		Optional<AuthorEntity> authorOptional = authorRepository.findByName(authorDto.getName());
		if(authorOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Autor com esse nome já cadastrado");
		
		var author = new AuthorEntity();
		BeanUtils.copyProperties(authorDto, author);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(authorRepository.save(author));
	}
	
	@PutMapping("/{authorId}")
	public ResponseEntity<?> updateAuthorById(@PathVariable UUID authorId, @RequestBody @Valid AuthorDto authorDto) {
		log.debug("Update Author: {}", authorDto.getName());
		
		Optional<AuthorEntity> authorOptional = authorRepository.findById(authorId);
		if(!authorOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		Optional<AuthorEntity> authorByNameOptional = authorRepository.findByName(authorDto.getName());
		if(authorByNameOptional.isPresent() && !authorByNameOptional.get().getAuthorId().equals(authorId)) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Autor com esse nome já cadastrado");
		
		var author = authorOptional.get();
		BeanUtils.copyProperties(authorDto, author);
		
		return ResponseEntity.ok(authorRepository.save(author));
	}
	
	@DeleteMapping("/{authorId}")
	public ResponseEntity<?> deleteAuthorById(@PathVariable UUID authorId) {
		Optional<AuthorEntity> authorOptional = authorRepository.findById(authorId);
		if(!authorOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		authorRepository.delete(authorOptional.get());
		
		return ResponseEntity.ok().build();
	}
	
}
