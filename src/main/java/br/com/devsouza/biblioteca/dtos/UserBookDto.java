package br.com.devsouza.biblioteca.dtos;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import br.com.devsouza.biblioteca.enums.Status;
import br.com.devsouza.biblioteca.enuns.Type;
import lombok.Data;

@Data
public class UserBookDto {

	@NotNull
	private Type type;
	
	@NotNull
	private Status status;
	
	@NotNull
	private UUID bookId;
	
}
