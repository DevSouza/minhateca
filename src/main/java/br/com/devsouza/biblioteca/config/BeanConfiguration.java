package br.com.devsouza.biblioteca.config;

import javax.persistence.EntityManager;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

	private final EntityManager em;
	
	// TODO: JPA Lazy ModelMapper
	
	@Bean
	public ModelMapper modelMapper() {
		 ModelMapper mapper = new ModelMapper();
		 mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
		 mapper.getConfiguration().setPropertyCondition(context -> {
//			 PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
			 //return unitUtil.isLoaded(context.getSource());
			 return !(context.getSource() instanceof PersistentCollection) || ((PersistentCollection) context.getSource()).wasInitialized() /*|| unitUtil.isLoaded(context.getSource())*/;
		 });		 
		 return mapper;
	}
	
}
