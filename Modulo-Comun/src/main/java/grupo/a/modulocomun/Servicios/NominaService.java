package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.LineaNominaDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.Entidades.*;
import grupo.a.modulocomun.Repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class NominaService {

    private final NominaRepository nominaRepository;
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public NominaService(NominaRepository nominaRepository, EmpleadoRepository empleadoRepository) {
        this.nominaRepository = nominaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public NominaDTO crearNomina(NominaDTO nominaDTO) {
        // Validar empleado
        Empleado empleado = empleadoRepository.findById(nominaDTO.getEmpleado().getId_empleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Crear la nómina
        Nomina nomina = new Nomina();
        nomina.setFecha(nominaDTO.getFecha() != null ? nominaDTO.getFecha() : LocalDate.now());
        nomina.setEmpleado(empleado);

        // Validar y agregar líneas
        if (nominaDTO.getLineas() == null || nominaDTO.getLineas().isEmpty()) {
            throw new RuntimeException("La nómina debe contener al menos una línea");
        }

        for (LineaNominaDTO lineaDTO : nominaDTO.getLineas()) {
            if (lineaDTO.getConcepto() == null || lineaDTO.getConcepto().trim().isEmpty()) {
                throw new RuntimeException("El concepto de la línea no puede estar vacío");
            }

            if (lineaDTO.getCantidad() == null || lineaDTO.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("El importe debe ser mayor que cero");
            }

            LineaNomina linea = new LineaNomina();
            linea.setDescripcion(lineaDTO.getConcepto());
            linea.setImporte(lineaDTO.getCantidad());
            linea.setNomina(nomina);
            nomina.getLineas().add(linea);
        }

        // Guardar la nómina
        Nomina nominaGuardada = nominaRepository.save(nomina);
        return convertirADTO(nominaGuardada);
    }

    private NominaDTO convertirADTO(Nomina nomina) {
        NominaDTO dto = new NominaDTO();
        dto.setId(nomina.getId());
        dto.setFecha(nomina.getFecha());

        // Convertir empleado a DTO
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setId_empleado(nomina.getEmpleado().getId_empleado());
        empleadoDTO.setNombre(nomina.getEmpleado().getNombre());
        empleadoDTO.setApellido(nomina.getEmpleado().getApellido());
        dto.setEmpleado(empleadoDTO);

        // Convertir líneas
        List<LineaNominaDTO> lineasDTO = nomina.getLineas().stream()
                .map(linea -> {
                    LineaNominaDTO lineaDTO = new LineaNominaDTO();
                    lineaDTO.setId(linea.getId());
                    lineaDTO.setConcepto(linea.getDescripcion());
                    lineaDTO.setCantidad(linea.getImporte());
                    return lineaDTO;
                })
                .collect(Collectors.toList());
        dto.setLineas(lineasDTO);

        // Calcular total
        BigDecimal total = nomina.getLineas().stream()
                .map(LineaNomina::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotal(total);

        return dto;
    }
    public List<NominaDTO> obtenerTodasNominas() {
        return nominaRepository.findAll().stream()
                .map(nomina -> {
                    NominaDTO dto = convertirADTO(nomina);
                    // Asegurar que el total esté calculado
                    if (dto.getTotal() == null) {
                        dto.calcularTotal();
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}