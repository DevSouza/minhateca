package br.com.devsouza.biblioteca.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devsouza.biblioteca.entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
	
	List<BookEntity> findByName(String name);
	
	@EntityGraph(attributePaths = { "authors", "genres" }, type = EntityGraphType.FETCH)
	Optional<BookEntity> findByBookId(UUID bookId);
	
}
