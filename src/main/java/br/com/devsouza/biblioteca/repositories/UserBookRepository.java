package br.com.devsouza.biblioteca.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devsouza.biblioteca.entities.UserBookEntity;
import br.com.devsouza.biblioteca.entities.UserEntity;

public interface UserBookRepository extends JpaRepository<UserBookEntity, UUID> {
	
	@EntityGraph(attributePaths = { "book", "book.authors" }, type = EntityGraphType.FETCH)
	List<UserBookEntity> findByUser(UserEntity user);
	
}
