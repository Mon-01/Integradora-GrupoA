package grupo.a.modulocomun.Servicios.Maestros;

import grupo.a.modulocomun.DTO.Maestros.EntidadBancariaDTO;
import grupo.a.modulocomun.Entidades.Maestros.EntidadBancaria;
import grupo.a.modulocomun.Repositorios.Maestros.EntidadBancariaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntidadBancariaService {
    @Autowired
    private EntidadBancariaRepository entidadBancariaRepository;

    public String obtenerCodPaisPorId(Long id){
        return entidadBancariaRepository.findById(id).get().getPais();
    }

    public String obtenerCodEntidadPorId(Long id){
        return entidadBancariaRepository.findById(id).get().getCod_entidad();
    }

    public String obtenerNombrePorId(Long id){
        return entidadBancariaRepository.findById(id)
                .map(EntidadBancaria::getNombre)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado la entidad bancaria con id: " +id));
    }

    public void cargarEntidadesBancarias() {
        if (entidadBancariaRepository.count() == 0) {
            entidadBancariaRepository.saveAll(List.of(
                    new EntidadBancaria("ES","BBVA","0182"),
                    new EntidadBancaria("ES", "Santander", "0049"),
                    new EntidadBancaria("ES", "CaixaBank", "2100"),
                    new EntidadBancaria("ES", "Banco Sabadell", "0081"),
                    new EntidadBancaria("ES", "ING", "1465"),
                    new EntidadBancaria("ES", "Bankinter", "0128")

            ));
        }
    }

    public List<EntidadBancariaDTO> obtenerEntidadesBancarias() {
        return entidadBancariaRepository.findAll().stream()
                .map( e -> new EntidadBancariaDTO(e.getId(),e.getNombre()))
                .collect(Collectors.toList());
    }
}
