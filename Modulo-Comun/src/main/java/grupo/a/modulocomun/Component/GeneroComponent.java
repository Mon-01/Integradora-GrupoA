package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Entidades.Genero;
import grupo.a.modulocomun.Repositorios.GeneroRepository;
import grupo.a.modulocomun.Servicios.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneroComponent implements CommandLineRunner {

    @Autowired
    private GeneroService generoService;

    @Override
    //se ejecuta al arrancar la aplicación
    public void run(String... args) throws Exception {
        generoService.cargarGeneros();
    }
}
