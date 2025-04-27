package grupo.a.modulocomun.Component.Maestros;

import grupo.a.modulocomun.Servicios.Maestros.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TipoDocumentoComponent implements CommandLineRunner {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Override
    public void run(String... args) throws Exception {
        tipoDocumentoService.cargarTipoDocumento();
    }
}
