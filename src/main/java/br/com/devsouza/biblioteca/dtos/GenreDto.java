package br.com.devsouza.biblioteca.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GenreDto {

	@NotBlank
	private String name;
}
