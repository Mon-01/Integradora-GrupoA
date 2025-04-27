package grupo.a.modulocomun.Servicios.Maestros;

import grupo.a.modulocomun.DTO.Maestros.GeneroDTO;
import grupo.a.modulocomun.Entidades.Maestros.Genero;
import grupo.a.modulocomun.Repositorios.Maestros.GeneroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public String obtenerNombrePorId(Long id) {
        return generoRepository.findById(id)
                .map(Genero::getNombre)
                .orElseThrow(() -> new EntityNotFoundException("Género no encontrado con id: " + id));
    }

    public void cargarGeneros() {
        //comrpueba si la tabla genero tiene datos
        if (generoRepository.count() == 0) {
            //al no tener crea una lista con los datos y los inserta
            generoRepository.saveAll(List.of(
                    new Genero("Masculino"),
                    new Genero("Femenino"),
                    new Genero("Otro")
            ));
        }
    }

    public List<GeneroDTO> obtenerTodos() {
        return generoRepository.findAll().stream()
                .map(g -> new GeneroDTO(g.getId(), g.getNombre()))
                .collect(Collectors.toList());
        /*
        obtiene los datos de la tabla genero con findAll y transforma cada objeto genero
        en generoDTO, con collect los almacena en una lista que es lo que devuelve
         */
    }
}

