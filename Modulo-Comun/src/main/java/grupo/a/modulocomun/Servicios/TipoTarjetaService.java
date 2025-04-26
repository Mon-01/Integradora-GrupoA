package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.TipoTarjetaDTO;
import grupo.a.modulocomun.Entidades.TipoTarjeta;
import grupo.a.modulocomun.Repositorios.TipoTarjetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoTarjetaService {

    private final TipoTarjetaRepository tipoTarjetaRepository;

    public void cargarTiposTarjeta() {
        if (tipoTarjetaRepository.count() == 0) {
            tipoTarjetaRepository.saveAll(List.of(
                    new TipoTarjeta("Débito"),
                    new TipoTarjeta("Crédito"),
                    new TipoTarjeta("Prepago"),
                    new TipoTarjeta("Virtual")
            ));
        }
    }

    public List<TipoTarjetaDTO> obtenerTodos() {
        return tipoTarjetaRepository.findAll()
                .stream()
                .map(t -> new TipoTarjetaDTO(t.getId(),t.getNombre()))
                .collect(Collectors.toList());
    }
}
