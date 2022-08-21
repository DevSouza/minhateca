package br.com.devsouza.biblioteca.controllers;

import java.util.Optional;
import java.util.UUID;

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

import br.com.devsouza.biblioteca.dtos.UserDto;
import br.com.devsouza.biblioteca.entities.UserEntity;
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
	
	//OK 1. Cadastrar Usuario	
	//OK 2. Atualizar cadastro do usuario
	//OK 3. Buscar por id
	
	// 4. Vincular livro
	// 5. Desvincular livro
	// 6. Listar livros do usuario
	
	
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
}
