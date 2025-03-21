package com.itau.localizacao_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Localização API",
		description = "API responsável por localizar endereço completo a partir de coordenadas",
		version = "1"
))
public class LocalizacaoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalizacaoApiApplication.class, args);
	}

}
