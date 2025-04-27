package grupo.a.modulocomun.Component.Maestros;

import grupo.a.modulocomun.Servicios.Maestros.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
