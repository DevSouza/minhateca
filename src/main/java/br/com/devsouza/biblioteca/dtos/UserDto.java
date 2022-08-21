package br.com.devsouza.biblioteca.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDto {

	private UUID userId;
	private String username;
	private String name;
	
}
