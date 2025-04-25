package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.GeneroDTO;
import grupo.a.modulocomun.Entidades.Genero;
import grupo.a.modulocomun.Repositorios.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

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

