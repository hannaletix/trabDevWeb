package com.example.trabalhoDevWeb;
import com.example.trabalhoDevWeb.libArvore.ArvoreAVLExemplo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Comparator;

@SpringBootApplication
@EnableAsync
public class TrabalhoDevWebApplication {
	@Bean
	public ArvoreAVLExemplo<Long> arvoreAVLExemplo() {
		// Configurando a instância da árvore AVL com um Comparator adequado.
		Comparator<Long> comparator = Comparator.naturalOrder();
		return new ArvoreAVLExemplo<>(comparator);
	}
	public static void main(String[] args) {
		SpringApplication.run(TrabalhoDevWebApplication.class, args);
	}
}
