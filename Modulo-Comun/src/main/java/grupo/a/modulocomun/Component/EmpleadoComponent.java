package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Servicios.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class EmpleadoComponent implements CommandLineRunner {

    @Autowired
    private EmpleadoService empleadoService;

    @Override
    public void run(String... args) throws Exception {
        empleadoService.cargarEmpleado();
    }
}
