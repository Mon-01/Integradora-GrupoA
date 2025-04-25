package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.GeneroDTO;
import grupo.a.modulocomun.DTO.PaisDTO;
import grupo.a.modulocomun.Entidades.Pais;
import grupo.a.modulocomun.Repositorios.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    public void cargarPaises(){
        if(paisRepository.count() == 0){
            paisRepository.saveAll(List.of(
                    new Pais("Alemania"),
                    new Pais("Francia"),
                    new Pais("España"),
                    new Pais("Portugal"),
                    new Pais("Italia")
            ));
        }
    }

    public List<PaisDTO> obtenerTodosPaises(){
        return paisRepository.findAll().stream()
                .map(p -> new PaisDTO(p.getId(),p.getNombre()))
                .collect(Collectors.toList());
    }

}
