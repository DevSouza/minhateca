package br.com.devsouza.biblioteca.domain;

import java.util.Set;
import java.util.UUID;

public class BookDomain {

	private UUID bookId;
	private String name;
	private Integer pages;
	private String urlCover;
	private Set<GenreDomain> genres;
	private Set<AuthorDomain> authors;
	
	public UUID getBookId() {
		return bookId;
	}
	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public String getUrlCover() {
		return urlCover;
	}
	public void setUrlCover(String urlCover) {
		this.urlCover = urlCover;
	}
	public Set<GenreDomain> getGenres() {
		return genres;
	}
	public void setGenres(Set<GenreDomain> genres) {
		this.genres = genres;
	}
	public Set<AuthorDomain> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<AuthorDomain> authors) {
		this.authors = authors;
	}
}
