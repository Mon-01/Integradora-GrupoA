package com.example.integradoragrupoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = {
        "com.example.aplicacioncorporativa",
        "com.example.aplicacionadministracion",
        "grupo.a.modulocomun"
})

public class IntegradoraGrupoAApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegradoraGrupoAApplication.class, args);
    }

}

