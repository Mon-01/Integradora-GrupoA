package grupo.a.modulocomun.Component;

import grupo.a.modulocomun.Servicios.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaisComponent {

    @Autowired
    private PaisService paisService;

    public void run (String... args){
        paisService.cargarPaises();
    }
}
