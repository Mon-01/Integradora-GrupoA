package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.Entidades.Nomina;
import grupo.a.modulocomun.Repositorios.DepartamentoRepository;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import grupo.a.modulocomun.Repositorios.Maestros.*;
import grupo.a.modulocomun.Repositorios.NominaRepository;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class RepositoryManager {
    private final GeneroRepository generoRepository;
    private final TipoViaRepository tipoViaRepository;
    private final PaisRepository paisRepository;
    private final TipoTarjetaRepository tipoTarjetaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final EspecialidadesRepository especialidadesRepository;
    private final EntidadBancariaRepository entidadBancariaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final NominaRepository nominaRepository;
}
