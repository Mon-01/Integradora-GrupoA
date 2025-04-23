package com.example.aplicacioncorporativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"grupo.a.modulocomun"
})
public class AplicacionCorporativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplicacionCorporativaApplication.class, args);
	}

}
