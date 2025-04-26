package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.TipoViaDTO;
import grupo.a.modulocomun.Entidades.TipoVia;
import grupo.a.modulocomun.Repositorios.TipoViaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoViaService {

    @Autowired
    TipoViaRepository tipoViaRepository;

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
