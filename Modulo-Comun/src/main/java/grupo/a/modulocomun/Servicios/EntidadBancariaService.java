package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EntidadBancariaDTO;
import grupo.a.modulocomun.DTO.EspecialidadesDTO;
import grupo.a.modulocomun.Entidades.EntidadBancaria;
import grupo.a.modulocomun.Repositorios.EntidadBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntidadBancariaService {
    @Autowired
    private EntidadBancariaRepository entidadBancariaRepository;

    public void cargarEntidadesBancarias() {
        if (entidadBancariaRepository.count() == 0) {
            entidadBancariaRepository.saveAll(List.of(
                    new EntidadBancaria("BBVA"),
                    new EntidadBancaria("Santander"),
                    new EntidadBancaria("CaixaBank"),
                    new EntidadBancaria("Banco Sabadell"),
                    new EntidadBancaria("ING"),
                    new EntidadBancaria("Bankinter")
            ));
        }
    }

    public List<EntidadBancariaDTO> obtenerEntidadesBancarias() {
        return entidadBancariaRepository.findAll().stream()
                .map( e -> new EntidadBancariaDTO(e.getId(),e.getNombre()))
                .collect(Collectors.toList());
    }
}
