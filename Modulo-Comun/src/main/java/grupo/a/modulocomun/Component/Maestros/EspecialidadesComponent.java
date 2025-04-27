package grupo.a.modulocomun.Component.Maestros;

import grupo.a.modulocomun.Servicios.Maestros.EspecialidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadesComponent implements CommandLineRunner{

    @Autowired
    private EspecialidadesService especialidadesService;

    @Override
    public void run(String... args) throws Exception {
        especialidadesService.cargarEspecialidades();
    }
}
