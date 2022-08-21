package br.com.devsouza.biblioteca.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.devsouza.biblioteca.dtos.BookDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_BOOK")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID bookId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Integer pages;
	
	@Column(nullable = true)
	private String urlCover;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(
    	name = "TB_BOOK_GENRE",
    	joinColumns = @JoinColumn(name = "book_id"),
    	inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<GenreEntity> genres;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(
    	name = "TB_BOOK_AUTHOR",
    	joinColumns = @JoinColumn(name = "book_id"),
    	inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<AuthorEntity> authors;
	
	public BookDto toBookDto() {
		var bookDto = new BookDto();
		BeanUtils.copyProperties(this, bookDto);
		return bookDto;
	}
	
}
