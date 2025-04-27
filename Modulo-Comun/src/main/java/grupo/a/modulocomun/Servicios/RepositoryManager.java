package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.Repositorios.DepartamentoRepository;
import grupo.a.modulocomun.Repositorios.Maestros.*;
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
}
