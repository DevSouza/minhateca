package br.com.devsouza.biblioteca.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	  
	  @Bean
	  public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription, @Value("${application-version}") String appVersion) {
//		  Components component = new Components()
//				  .addSecuritySchemes("AccessToken", new SecurityScheme()
//						  .name("Authorization")
//						  .description("Header Authorization is compost per typeToken + ' ' + accessToken")
//						  .type(Type.APIKEY)
//						  .in(In.HEADER)
//						  .bearerFormat("JWT"));
		  
		  Info info = new Info()
				  .title("Minha Teca API")
				  .description(appDescription)
				  .version(appVersion);
		  
		  return new OpenAPI()
//				  .components(component)
				  //.addSecurityItem(new SecurityRequirement().addList("AccessToken"))
				  .info(info);
	  }
}
