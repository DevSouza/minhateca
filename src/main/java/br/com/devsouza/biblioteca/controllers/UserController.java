package br.com.devsouza.biblioteca.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devsouza.biblioteca.domain.UserBookDomain;
import br.com.devsouza.biblioteca.dtos.UserBookDto;
import br.com.devsouza.biblioteca.dtos.UserDto;
import br.com.devsouza.biblioteca.entities.BookEntity;
import br.com.devsouza.biblioteca.entities.UserBookEntity;
import br.com.devsouza.biblioteca.entities.UserEntity;
import br.com.devsouza.biblioteca.repositories.BookRepository;
import br.com.devsouza.biblioteca.repositories.UserBookRepository;
import br.com.devsouza.biblioteca.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final ModelMapper mapper;
	
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	private final UserBookRepository userBookRepository;

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
		log.debug("Get User by id: {}", userId);
		
		Optional<UserEntity> userOptional = userRepository.findById(userId);
		if(!userOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(userOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> insertUser(@RequestBody @Valid UserDto userDto) {
		log.debug("Insert User: {}", userDto.getName());
		
		Optional<UserEntity> userOptional = userRepository.findByUsername(userDto.getUsername());
		if(userOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username já cadastrado");
		
		var user = new UserEntity();
		BeanUtils.copyProperties(userDto, user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUserById(@PathVariable UUID userId, @RequestBody @Valid UserDto userDto) {
		log.debug("Update Book: {}", userDto.getName());
		
		Optional<UserEntity> userOptional = userRepository.findById(userId);
		if(!userOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		Optional<UserEntity> userByUsernameOptional = userRepository.findByUsername(userDto.getUsername());
		if(userByUsernameOptional.isPresent() && !userByUsernameOptional.get().getUserId().equals(userId)) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username já cadastrado");
		
		var user = userOptional.get();
		mapper.map(userDto, user);
		user.setUserId(userId);
		
		return ResponseEntity.ok(userRepository.save(user));
	}

	@GetMapping("/{userId}/books")
	public ResponseEntity<?> getAllBooksByUser(@PathVariable UUID userId) {
		log.debug("Get Books by User: {}", userId);
		
		Optional<UserEntity> userOptional = userRepository.findById(userId);
		if(!userOptional.isPresent()) 
			return ResponseEntity.notFound().build();
		
		List<UserBookEntity> lista = userBookRepository.findByUser(userOptional.get());
		
		log.info("Antes do mapper");
		
		return ResponseEntity.ok(userBookEntityToUserBookDomain(lista));
	}

	private List<UserBookDomain> userBookEntityToUserBookDomain(List<UserBookEntity> userBooks) {
		return userBooks.stream().map(item -> {
			UserBookDomain userBook = new UserBookDomain();
			mapper.map(item, userBook);
			return userBook;
		}).collect(Collectors.toList());
	}
	
	@PostMapping("/{userId}/books")
	public ResponseEntity<?> attachBookToUser(@PathVariable UUID userId, @RequestBody @Valid UserBookDto userBookDto) {
		log.debug("Attach Book {} to User {}", userBookDto.getBookId(), userId);
		
		Optional<UserEntity> userOptional = userRepository.findById(userId);
		if(!userOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe");
		
		Optional<BookEntity> bookOptional = bookRepository.findById(userBookDto.getBookId());
		if(!bookOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não existe");
		
		var userBook = new UserBookEntity();
		mapper.map(userBookDto, userBook);
		userBook.setUser(userOptional.get());
		userBook.setBook(bookOptional.get());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userBookRepository.save(userBook));
	}
	
	// Desvincular livro
}
