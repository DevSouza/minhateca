package br.com.devsouza.biblioteca.domain;

import java.util.Set;
import java.util.UUID;

import br.com.devsouza.biblioteca.enuns.Gender;

public class AuthorDomain {

	private UUID authorId;
	private String name;
	private Gender gender;
	private Set<BookDomain> books;
	
	public UUID getAuthorId() {
		return authorId;
	}
	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Set<BookDomain> getBooks() {
		return books;
	}
	public void setBooks(Set<BookDomain> books) {
		this.books = books;
	}
	
	
}
