package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Servicios.TipoViaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TipoViaComponent implements CommandLineRunner {

    @Autowired
    TipoViaService tipoViaService;

    @Override
    public void run(String... args) throws Exception {
        tipoViaService.cargarTipoVia();
    }
}
