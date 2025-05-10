package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.DepartamentoDTO;
import grupo.a.modulocomun.Entidades.Departamento;
import grupo.a.modulocomun.Repositorios.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public String obtenerNombrePorId(Long id){
        return departamentoRepository.findById(id)
                .map(Departamento::getNombre_dept)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el departamento con id: " +id));
    }

    public void cargarDepartamentos() {
        if (departamentoRepository.count() == 0) {
            departamentoRepository.saveAll(List.of(
                    new Departamento("Administración", "ADM", "Madrid", new BigDecimal("50000.00")),
                    new Departamento("Recursos Humanos", "RRHH", "Barcelona", new BigDecimal("30000.00")),
                    new Departamento("Tecnología", "TEC", "Valencia", new BigDecimal("70000.00")),
                    new Departamento("Marketing", "MAR", "Cáceres", new BigDecimal("10000.00"))
            ));
        }
    }

    public List<DepartamentoDTO> obtenerTodos() {
        return departamentoRepository.findAll()
                .stream()
                //this::funcion == d -> convertirDTO(d)
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    //metodo para dinamizar el paso de entidad a dto por ser una tabla con varias columnas
    public DepartamentoDTO convertirDTO(Departamento departamento) {
        return new DepartamentoDTO(
                departamento.getId_dept(),
                departamento.getNombre_dept(),
                departamento.getCod(),
                departamento.getLoc(),
                departamento.getPresupuesto()
        );
    }
}
