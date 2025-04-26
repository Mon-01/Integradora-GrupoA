package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.TipoDocumentoDTO;
import grupo.a.modulocomun.Entidades.TipoDocumento;
import grupo.a.modulocomun.Repositorios.TipoDocumentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public String obtenerNombrePorId(Long id){
        return tipoDocumentoRepository.findById(id)
                .map(TipoDocumento::getNombre)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado con id " +id));
    }

    public void cargarTipoDocumento() {
        if (tipoDocumentoRepository.count() == 0) {
            //al no tener crea una lista con los datos y los inserta
            tipoDocumentoRepository.saveAll(List.of(
                    new TipoDocumento("DNI"),
                    new TipoDocumento("NIF"),
                    new TipoDocumento("NIE")
            ));
        }
    }

    public List<TipoDocumentoDTO> obtenerTiposDocumento() {
        return tipoDocumentoRepository.findAll().stream()
                .map(t -> new TipoDocumentoDTO(t.getId(),t.getNombre()))
                .collect(Collectors.toList());
    }
}
