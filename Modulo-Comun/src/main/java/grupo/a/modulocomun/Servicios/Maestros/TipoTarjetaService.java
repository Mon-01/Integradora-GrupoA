package grupo.a.modulocomun.Servicios.Maestros;

import grupo.a.modulocomun.DTO.Maestros.TipoTarjetaDTO;
import grupo.a.modulocomun.Entidades.Maestros.TipoTarjeta;
import grupo.a.modulocomun.Repositorios.Maestros.TipoTarjetaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoTarjetaService {

    @Autowired
    private final TipoTarjetaRepository tipoTarjetaRepository;

    public String obtenerNombrePorId(Long id){
        return tipoTarjetaRepository.findById(id)
                .map(TipoTarjeta::getNombre)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el tipo de tarjeta con id: " +id));
    }

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
