package grupo.a.modulocomun.Component.Maestros;

import grupo.a.modulocomun.Servicios.Maestros.EntidadBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EntidadBancariaComponent implements CommandLineRunner {
    @Autowired
    private EntidadBancariaService entidadBancariaService;

    @Override
    public void run(String... args) throws Exception {
        entidadBancariaService.cargarEntidadesBancarias();
    }
}
