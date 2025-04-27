package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.Servicios.Maestros.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Getter
@Service
//servicio auxiliar para no llamar a 400 servicios en el controlador
public class DatosAuxiliaresService {
    private final GeneroService generoService;
    private final PaisService paisService;
    private final TipoViaService tipoViaService;
    private final TipoDocumentoService tipoDocumentoService;
    private final DepartamentoService departamentoService;
    private final EspecialidadesService especialidadesService;
    private final EntidadBancariaService entidadBancariaService;
    private final TipoTarjetaService tipoTarjetaService;
}
