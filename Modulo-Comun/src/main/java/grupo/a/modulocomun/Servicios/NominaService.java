package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.LineaNominaDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.DTO.filtros.devueltaFiltroNominasDTO;
import grupo.a.modulocomun.Entidades.*;
import grupo.a.modulocomun.Repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private final EmpleadoService empleadoService;

    @Autowired
    public NominaService(NominaRepository nominaRepository, EmpleadoRepository empleadoRepository, EmpleadoService empleadoService) {
        this.nominaRepository = nominaRepository;
        this.empleadoRepository = empleadoRepository;
        this.empleadoService = empleadoService;
    }
    public void eliminarNomina(Long id) {
        // Esto eliminará en cascada las líneas de nómina debido a la configuración CascadeType.ALL
        nominaRepository.deleteById(id);
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
/*
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

 */
    public List<NominaDTO> obtenerNominasPorEmpleado(Long empleadoId) {
        return nominaRepository.findNominasConLineasByEmpleadoId(empleadoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public devueltaFiltroNominasDTO returnConsultaFiltradoNominas (Nomina nomina) {
        devueltaFiltroNominasDTO filtrado = new devueltaFiltroNominasDTO();
        filtrado.setNombre(nomina.getEmpleado().getNombre());
        filtrado.setIdUsuario(nomina.getEmpleado().getId_empleado());
        filtrado.setFecha(nomina.getFecha());
        return filtrado;
    }


    public NominaDTO convertirADTO(Nomina nomina) {
        NominaDTO dto = new NominaDTO();
        dto.setId(nomina.getId());
        dto.setFecha(nomina.getFecha());
        dto.setEmpleado(empleadoService.convertirEmpleadoADTO(nomina.getEmpleado()));

        // Convertir líneas
        List<LineaNominaDTO> lineas = nomina.getLineas().stream()
                .map(linea -> {
                    LineaNominaDTO lineaDTO = new LineaNominaDTO();
                    lineaDTO.setId(linea.getId());
                    lineaDTO.setConcepto(linea.getDescripcion());
                    lineaDTO.setCantidad(linea.getImporte());
                    return lineaDTO;
                })
                .collect(Collectors.toList());
        dto.setLineas(lineas);

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
/*
    public List<Nomina> filtrarPorNomina(String nombre,LocalDate fecha){
        if( (nombre == null || nombre.trim().isEmpty())){
            return nominaRepository.findAll(Sort.by("empleado.nombre"));
        }
        return nominaRepository.filtroNomina(nombre);
    }

 */
public List<Nomina> filtrarPorNomina(String nombre, String departamento, LocalDate fecha) {
    return nominaRepository.filtroNomina(nombre, departamento, fecha);
}

//    public void cargarNominas() {
//            List<Empleado> empleados = empleadoRepository.findAll();
//            List<Nomina> nominas = empleados.stream().map(empleado -> {
//                Nomina nomina = new Nomina();
//                nomina.setFecha(LocalDate.now());
//                nomina.setEmpleado(empleado);
//
//                // Crear líneas de nómina (ajusta los importes y las descripciones según sea necesario)
//                LineaNomina linea1 = new LineaNomina("Salario Base", new BigDecimal("1500.00"));
//                LineaNomina linea2 = new LineaNomina("Bonificación", new BigDecimal("300.00"));
//                LineaNomina linea3 = new LineaNomina("Horas Extra", new BigDecimal("200.00"));
//
//                // Asociar las líneas de nómina con la nómina
//                nomina.agregarLinea(linea1);
//                nomina.agregarLinea(linea2);
//                nomina.agregarLinea(linea3);
//
//                return nomina;
//            }).collect(Collectors.toList());
//
//            nominaRepository.saveAll(nominas);
//        System.out.println("nominas guardadas, supuestamente");
//    }
}