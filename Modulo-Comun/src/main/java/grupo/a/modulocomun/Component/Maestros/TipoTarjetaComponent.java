package grupo.a.modulocomun.Component.Maestros;

import grupo.a.modulocomun.Servicios.Maestros.TipoTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TipoTarjetaComponent implements CommandLineRunner {

    @Autowired
    private TipoTarjetaService tipoTarjetaService;

    @Override
    public void run(String... args) throws Exception {
        tipoTarjetaService.cargarTiposTarjeta();
    }
}
