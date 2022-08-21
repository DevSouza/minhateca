package br.com.devsouza.biblioteca.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_GENRE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID genreId;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "genres", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<BookEntity> books;
	
}
