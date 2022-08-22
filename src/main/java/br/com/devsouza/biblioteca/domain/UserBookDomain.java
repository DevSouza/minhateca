package br.com.devsouza.biblioteca.domain;

import java.util.UUID;

import br.com.devsouza.biblioteca.enums.Status;
import br.com.devsouza.biblioteca.enuns.Type;

public class UserBookDomain {

	private UUID userBookId;
	private Type type;
	private Status status = Status.NOT_STARTED;
	private UserDomain user;
	private BookDomain book;
	
	public UUID getUserBookId() {
		return userBookId;
	}
	public void setUserBookId(UUID userBookId) {
		this.userBookId = userBookId;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public UserDomain getUser() {
		return user;
	}
	public void setUser(UserDomain user) {
		this.user = user;
	}
	public BookDomain getBook() {
		return book;
	}
	public void setBook(BookDomain book) {
		this.book = book;
	}
}
