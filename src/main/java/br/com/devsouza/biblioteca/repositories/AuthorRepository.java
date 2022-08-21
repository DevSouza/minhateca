package br.com.devsouza.biblioteca.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devsouza.biblioteca.entities.AuthorEntity;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {
	
	Optional<AuthorEntity> findByName(String name);
	
}
