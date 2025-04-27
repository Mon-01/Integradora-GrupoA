package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.Repositorios.Maestros.GeneroRepository;
import grupo.a.modulocomun.Repositorios.Maestros.PaisRepository;
import grupo.a.modulocomun.Repositorios.Maestros.TipoTarjetaRepository;
import grupo.a.modulocomun.Repositorios.Maestros.TipoViaRepository;
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


}
