package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EspecialidadesDTO;
import grupo.a.modulocomun.Entidades.Especialidades;
import grupo.a.modulocomun.Repositorios.EspecialidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadesService {
    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    public void cargarEspecialidades() {
        if (especialidadesRepository.count() == 0) {
            especialidadesRepository.saveAll(List.of(
                    new Especialidades("Logística"),
                    new Especialidades("Consultoría"),
                    new Especialidades("Análisis de datos"),
                    new Especialidades("Gestión de calidad"),
                    new Especialidades("Servicio al cliente")
            ));
        }
    }

    public List<EspecialidadesDTO> obtenerEspecialidades() {
        return especialidadesRepository.findAll().stream()
                .map( e -> new EspecialidadesDTO(e.getId(),e.getNombre()))
                .collect(Collectors.toList());
    }

}
