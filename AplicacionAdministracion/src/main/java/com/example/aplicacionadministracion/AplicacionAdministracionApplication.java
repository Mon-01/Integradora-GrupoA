package com.example.aplicacionadministracion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "grupo.a.modulocomun"
})
public class AplicacionAdministracionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AplicacionAdministracionApplication.class, args);
    }

}
