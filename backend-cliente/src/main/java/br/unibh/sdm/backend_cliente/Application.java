package br.unibh.sdm.backend_cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * Classe executavel que inicia a aplicacao Spring Boot
 * @author jhcru
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.unibh.sdm.backend_cliente.persistencia")
@OpenAPIDefinition(info = @Info(title = "Criptomoeda API", version = "1.0", description = "API de Criptomoeda e Cotação", 
	license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
	contact = @Contact(name = "Suporte da Empresa XPTO", email = "suporte@empresa.com"), 
	termsOfService = "http://empresa.com/termos_uso_api")	)
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	// metodo main
	public static void main(String[] args) {
		log.info("Inicializando...");
	    System.setProperty("server.servlet.context-path", "/cliente-api");
	    System.setProperty("server.port", "8080");
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}
	
}
