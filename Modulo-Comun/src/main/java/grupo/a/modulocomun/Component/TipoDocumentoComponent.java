package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Servicios.TipoDocumentoService;
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
