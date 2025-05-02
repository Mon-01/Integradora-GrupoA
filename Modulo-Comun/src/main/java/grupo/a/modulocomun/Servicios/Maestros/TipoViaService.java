package grupo.a.modulocomun.Servicios.Maestros;

import grupo.a.modulocomun.DTO.Maestros.TipoViaDTO;
import grupo.a.modulocomun.Entidades.Maestros.TipoVia;
import grupo.a.modulocomun.Repositorios.Maestros.TipoViaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoViaService {

    @Autowired
    TipoViaRepository tipoViaRepository;

    public String obtenerNombrePorId(Long id){
        return tipoViaRepository.findById(id)
                .map(TipoVia::getNombre)
                .orElseThrow(() -> new EntityNotFoundException("Tipo Via no encontrado con id: " + id));
    }

    public void cargarTipoVia() {
        if(tipoViaRepository.count() == 0){
            tipoViaRepository.saveAll(List.of(
                    new TipoVia("Calle"),
                    new TipoVia("Avenida"),
                    new TipoVia("Plaza"),
                    new TipoVia("Carretera")
            ));
        }
    }

    public List<TipoViaDTO> obtenerTipoVia() {
        return tipoViaRepository.findAll().stream()
                .map( t -> new TipoViaDTO(t.getId(),t.getNombre()))
                .collect(Collectors.toList());
    }
}
