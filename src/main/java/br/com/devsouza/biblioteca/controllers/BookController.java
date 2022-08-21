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

import br.com.devsouza.biblioteca.dtos.BookDto;
import br.com.devsouza.biblioteca.entities.AuthorEntity;
import br.com.devsouza.biblioteca.entities.BookEntity;
import br.com.devsouza.biblioteca.entities.GenreEntity;
import br.com.devsouza.biblioteca.repositories.AuthorRepository;
import br.com.devsouza.biblioteca.repositories.BookRepository;
import br.com.devsouza.biblioteca.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final GenreRepository genreRepository;

	@GetMapping
	public ResponseEntity<?> getAllBooks() {
		log.debug("Get All Book");
		
		return ResponseEntity.ok(bookRepository.findAll());
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<?> getBookById(@PathVariable UUID bookId) {
		log.debug("Get Book by id: {}", bookId);
		
		Optional<BookEntity> bookOptional = bookRepository.findByBookId(bookId);
		if(!bookOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(bookOptional.get().toBookDto());
	}
	
	@PostMapping
	public ResponseEntity<?> insertBook(@RequestBody @Valid BookDto bookDto) {
		log.debug("Insert Book: {}", bookDto.getName());
		
		var book = new BookEntity();
		BeanUtils.copyProperties(bookDto, book);
		
		for(AuthorEntity author : bookDto.getAuthors()) {
			if(!authorExists(author)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor com id: " + author.getAuthorId() + " não existe");					
			book.getAuthors().add(author);
		}
		
		for(GenreEntity genre : bookDto.getGenres()) {
			if(!genreExists(genre)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero com id: " + genre.getGenreId() + " não existe");
			book.getGenres().add(genre);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(book));
	}

	private boolean authorExists(AuthorEntity item) {
		return authorRepository.findById(item.getAuthorId()).isPresent();
	}
	private boolean genreExists(GenreEntity item) {
		return genreRepository.findById(item.getGenreId()).isPresent();
	}
	
	@PutMapping("/{bookId}")
	public ResponseEntity<?> updateBookById(@PathVariable UUID bookId, @RequestBody @Valid BookDto bookDto) {
		log.debug("Update Book: {}", bookDto.getName());
		
		Optional<BookEntity> bookOptional = bookRepository.findById(bookId);
		if(!bookOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		var book = bookOptional.get();
		BeanUtils.copyProperties(bookDto, book, "bookId");
		
		for(AuthorEntity author : bookDto.getAuthors()) {
			if(!authorExists(author)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor com id: " + author.getAuthorId() + " não existe");					
			book.getAuthors().add(author);
		}
		
		for(GenreEntity genre : bookDto.getGenres()) {
			if(!genreExists(genre)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero com id: " + genre.getGenreId() + " não existe");
			book.getGenres().add(genre);
		}
		
		return ResponseEntity.ok(bookRepository.save(book));
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseEntity<?> deleteBookById(@PathVariable UUID bookId) {
		Optional<BookEntity> bookOptional = bookRepository.findById(bookId);
		if(!bookOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		bookRepository.delete(bookOptional.get());
		
		return ResponseEntity.ok().build();
	}
	
}
