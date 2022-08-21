package br.com.devsouza.biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	// 1. Cadastrar Usuario
	// 2. Atualizar cadastro do usuario
	// 3. Vincular livro
	// 4. Desvincular livro
	// 5. Listar livros do usuario
	
}
