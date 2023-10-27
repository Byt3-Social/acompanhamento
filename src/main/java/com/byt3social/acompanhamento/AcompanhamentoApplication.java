package com.byt3social.acompanhamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API Acompanhamento",
        version = "1.0",
        description = "Api destinada ao acompanhamento de organizações em suas ações sociais, por meio de reuniões e indicadores."
    )
)public class AcompanhamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcompanhamentoApplication.class, args);
	}

}
