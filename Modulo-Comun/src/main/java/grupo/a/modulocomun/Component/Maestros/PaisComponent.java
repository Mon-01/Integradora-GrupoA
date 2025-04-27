package grupo.a.modulocomun.Component.Maestros;

import grupo.a.modulocomun.Servicios.Maestros.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PaisComponent implements CommandLineRunner {

    @Autowired
    private PaisService paisService;

    @Override
    public void run (String... args) throws Exception{
        paisService.cargarPaises();
    }
}
