package br.com.devsouza.biblioteca.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devsouza.biblioteca.entities.GenreEntity;

public interface GenreRepository extends JpaRepository<GenreEntity, UUID> {
	
	Optional<GenreEntity> findByName(String name);
	
}
