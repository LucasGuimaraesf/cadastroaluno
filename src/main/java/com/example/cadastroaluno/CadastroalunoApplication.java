package com.example.cadastroaluno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Esse comando diz ao Spring: Varra esse pacote, encontre meus Controllers/Models/etc e suba o servidor web/
public class CadastroalunoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroalunoApplication.class, args);
	}
}