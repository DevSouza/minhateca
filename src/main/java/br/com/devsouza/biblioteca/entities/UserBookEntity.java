package br.com.devsouza.biblioteca.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.devsouza.biblioteca.enuns.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER_BOOK")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userBookId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Type typeBook;
	
	@JoinColumn(name = "user_id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserEntity user;
	
	@JoinColumn(name = "book_id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BookEntity book;
	
}
