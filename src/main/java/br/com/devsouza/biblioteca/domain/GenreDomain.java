package br.com.devsouza.biblioteca.domain;

import java.util.Set;
import java.util.UUID;

public class GenreDomain {

	private UUID genreId;
	private String name;
	private Set<BookDomain> books;
	
	public UUID getGenreId() {
		return genreId;
	}
	public void setGenreId(UUID genreId) {
		this.genreId = genreId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<BookDomain> getBooks() {
		return books;
	}
	public void setBooks(Set<BookDomain> books) {
		this.books = books;
	}
}
