package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Servicios.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DepartamentoComponent implements CommandLineRunner {
    @Autowired
    private DepartamentoService departamentoService;
    @Override
    public void run(String... args) throws Exception {
        departamentoService.cargarDepartamentos();
    }
}
