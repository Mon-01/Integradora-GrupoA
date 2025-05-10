package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Servicios.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//se ejecuta antes que el EmpleadoComponent
@Order(1)
public class MaestrosComponent implements CommandLineRunner {

    @Autowired
    private ServiceManager serviceManager;

    @Override
    public void run(String... args) throws Exception {
        serviceManager.getEntidadBancariaService().cargarEntidadesBancarias();
        serviceManager.getEspecialidadesService().cargarEspecialidades();
        serviceManager.getGeneroService().cargarGeneros();
        serviceManager.getPaisService().cargarPaises();
        serviceManager.getTipoDocumentoService().cargarTipoDocumento();
        serviceManager.getTipoTarjetaService().cargarTiposTarjeta();
        serviceManager.getTipoViaService().cargarTipoVia();
        serviceManager.getDepartamentoService().cargarDepartamentos();
        //serviceManager.getNominaService().cargarNominas();
    }
}
