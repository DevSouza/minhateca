package br.com.devsouza.biblioteca.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devsouza.biblioteca.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	
	Optional<UserEntity> findByUsername(String username);
	
}
