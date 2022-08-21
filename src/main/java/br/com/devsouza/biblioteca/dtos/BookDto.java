package br.com.devsouza.biblioteca.dtos;

import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.devsouza.biblioteca.entities.AuthorEntity;
import br.com.devsouza.biblioteca.entities.GenreEntity;
import lombok.Data;

@Data
public class BookDto {

	private UUID bookId;
	
	@NotBlank
	private String name;
	
	@NotNull
	private Integer pages;
	
	private String urlCover;
	
	@NotEmpty
	private Set<GenreEntity> genres;
	
	@NotEmpty
	private Set<AuthorEntity> authors;
}
