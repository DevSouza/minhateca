package br.com.devsouza.biblioteca.dtos;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.devsouza.biblioteca.enuns.Gender;
import lombok.Data;

@Data
public class AuthorDto {

	@NotBlank
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
}
