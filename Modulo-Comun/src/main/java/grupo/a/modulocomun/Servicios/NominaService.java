package grupo.a.modulocomun.Servicios;

// Importaciones para generación de PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Importaciones de DTOs
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.LineaNominaDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.DTO.filtros.filtrosNominasDTO;

// Importaciones de entidades
import grupo.a.modulocomun.Entidades.*;

// Importaciones de repositorios
import grupo.a.modulocomun.Repositorios.*;

// Importaciones para manejo de excepciones
import jakarta.persistence.EntityNotFoundException;

// Importaciones para mapeo de objetos
import org.modelmapper.ModelMapper;

// Importaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Importaciones para manejo de streams
import java.io.ByteArrayOutputStream;

// Importaciones para manejo de números
import java.math.BigDecimal;
import java.math.RoundingMode;

// Importaciones para manejo de recursos
import java.net.URL;

// Importaciones para manejo de fechas
import java.time.LocalDate;

// Importaciones para comparación
import java.util.Comparator;

// Importaciones para colecciones
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de nóminas
 * Maneja la creación, consulta y generación de reportes de nóminas
 */
@Service // Indica que esta clase es un componente de servicio gestionado por Spring
@Transactional // Todas las operaciones son transaccionales
public class NominaService {

    // Repositorio para operaciones CRUD de nóminas
    private final NominaRepository nominaRepository;

    // Repositorio para operaciones con empleados
    private final EmpleadoRepository empleadoRepository;

    // Servicio para operaciones con empleados
    private final EmpleadoService empleadoService;

    // Gestor centralizado de repositorios
    private final RepositoryManager repositoryManager;

    // Mapeador de objetos
    private final ModelMapper modelMapper;

    /**
     * Constructor principal para inyección de dependencias
     * @param nominaRepository Repositorio de nóminas
     * @param empleadoRepository Repositorio de empleados
     * @param empleadoService Servicio de empleados
     * @param repositoryManager Gestor de repositorios
     * @param modelMapper Mapeador de objetos
     */
    @Autowired
    public NominaService(NominaRepository nominaRepository,
                         EmpleadoRepository empleadoRepository,
                         EmpleadoService empleadoService,
                         RepositoryManager repositoryManager,
                         ModelMapper modelMapper) {
        this.nominaRepository = nominaRepository;
        this.empleadoRepository = empleadoRepository;
        this.empleadoService = empleadoService;
        this.repositoryManager = repositoryManager;
        this.modelMapper = modelMapper;
    }

    /**
     * Elimina una nómina por su ID
     * @param id ID de la nómina a eliminar
     */
    public void eliminarNomina(Long id) {
        // La eliminación en cascada está configurada en la entidad Nomina
        nominaRepository.deleteById(id);
    }

    /**
     * Crea una nueva nómina en el sistema
     * @param nominaDTO DTO con los datos de la nómina
     * @return DTO de la nómina creada
     */
    public NominaDTO crearNomina(NominaDTO nominaDTO) {
        // Valida que el empleado exista
        Empleado empleado = empleadoRepository.findById(nominaDTO.getEmpleado().getId_empleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Crea la nueva nómina
        Nomina nomina = new Nomina();
        nomina.setFecha(nominaDTO.getFecha() != null ? nominaDTO.getFecha() : LocalDate.now());
        nomina.setEmpleado(empleado);

        // Valida que haya líneas en la nómina
        if (nominaDTO.getLineas() == null || nominaDTO.getLineas().isEmpty()) {
            throw new RuntimeException("La nómina debe contener al menos una línea");
        }

        // Obtiene el salario base para cálculos
        BigDecimal salarioAnual = empleado.getSalarioAnual();

        // Procesa cada línea de la nómina
        for (LineaNominaDTO lineaDTO : nominaDTO.getLineas()) {
            // Validaciones básicas
            if (lineaDTO.getConcepto() == null || lineaDTO.getConcepto().trim().isEmpty()) {
                throw new RuntimeException("El concepto de la línea no puede estar vacío");
            }

            if (lineaDTO.getEsDevengo() == null) {
                throw new RuntimeException("Debe especificar si la línea es devengo o deducción");
            }

            // Crea y configura la línea
            LineaNomina linea = new LineaNomina();
            linea.setDescripcion(lineaDTO.getConcepto());
            linea.setEsDevengo(lineaDTO.getEsDevengo());

            BigDecimal importe;

            // Calcula el importe según porcentaje o importe fijo
            if (lineaDTO.getPorcentaje() != null && !lineaDTO.getPorcentaje().isEmpty()) {
                BigDecimal porcentaje = new BigDecimal(lineaDTO.getPorcentaje());
                linea.setPorcentaje(porcentaje);
                importe = salarioAnual.multiply(porcentaje)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            } else if (lineaDTO.getImporteFijo() != null && !lineaDTO.getImporteFijo().isEmpty()) {
                importe = new BigDecimal(lineaDTO.getImporteFijo());
                linea.setImporteFijo(importe);
            } else {
                throw new RuntimeException("Debe especificar porcentaje o importe fijo");
            }

            // Si es deducción, hace el importe negativo
            if (!lineaDTO.getEsDevengo()) {
                importe = importe.negate();
            }

            linea.setImporte(importe);
            linea.setNomina(nomina);
            nomina.getLineas().add(linea);
        }

        // Guarda la nómina y devuelve el DTO
        Nomina nominaGuardada = nominaRepository.save(nomina);
        return convertirADTO(nominaGuardada);
    }

    /**
     * Obtiene una nómina por su ID
     * @param id ID de la nómina
     * @return Entidad Nomina encontrada
     */
    public Nomina obtenerNomina(Long id) {
        return nominaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nómina no encontrada con id: " + id));
    }

    /**
     * Obtiene todas las nóminas de un empleado
     * @param empleadoId ID del empleado
     * @return Lista de DTOs de nóminas
     */
    public List<NominaDTO> obtenerNominasPorEmpleado(Long empleadoId) {
        return nominaRepository.findNominasConLineasByEmpleadoId(empleadoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una nómina a DTO para filtrado
     * @param nomina Entidad a convertir
     * @return DTO para filtrado
     */
    public filtrosNominasDTO returnConsultaFiltradoNominas(Nomina nomina) {
        filtrosNominasDTO filtrado = new filtrosNominasDTO();

        // Datos básicos del empleado
        filtrado.setNombre(nomina.getEmpleado().getNombre());
        filtrado.setId(nomina.getEmpleado().getId_empleado());
        filtrado.setFecha(nomina.getFecha());

        // Cálculo del total de la nómina
        BigDecimal total = nomina.getLineas().stream()
                .map(LineaNomina::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        filtrado.setSalario(total);

        // Departamento del empleado
        if(nomina.getEmpleado() != null && nomina.getEmpleado().getDepartamento() != null){
            filtrado.setDepartamento(nomina.getEmpleado().getDepartamento().getNombredept());
        }else {
            filtrado.setDepartamento("Sin departamento");
        }
        return filtrado;
    }

    /**
     * Convierte una entidad Nomina a su representación DTO
     * @param nomina Entidad a convertir
     * @return DTO con los datos de la nómina
     */
    public NominaDTO convertirADTO(Nomina nomina) {
        NominaDTO dto = new NominaDTO();

        // Datos básicos
        dto.setId(nomina.getId());
        dto.setFecha(nomina.getFecha());
        dto.setEmpleado(empleadoService.convertirEmpleadoADTO(nomina.getEmpleado()));

        // Conversión de líneas
        List<LineaNominaDTO> lineas = nomina.getLineas().stream()
                .map(linea -> {
                    LineaNominaDTO lineaDTO = new LineaNominaDTO();
                    lineaDTO.setId(linea.getId());
                    lineaDTO.setConcepto(linea.getDescripcion());
                    lineaDTO.setEsDevengo(linea.getEsDevengo());

                    // Porcentaje si existe
                    if (linea.getPorcentaje() != null) {
                        lineaDTO.setPorcentaje(linea.getPorcentaje().toString());
                    }

                    // Importe fijo si existe
                    if (linea.getImporteFijo() != null) {
                        lineaDTO.setImporteFijo(linea.getImporteFijo().toString());
                    }

                    // Cantidad calculada
                    lineaDTO.setCantidad(linea.getImporte() != null ? linea.getImporte().toString() : "0.00");
                    return lineaDTO;
                })
                .collect(Collectors.toList());
        dto.setLineas(lineas);

        // Cálculo del total
        BigDecimal total = nomina.getLineas().stream()
                .map(LineaNomina::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotal(total);

        return dto;
    }

    /**
     * Obtiene todas las nóminas del sistema
     * @return Lista de DTOs de nóminas
     */
    public List<NominaDTO> obtenerTodasNominas() {
        return nominaRepository.findAll().stream()
                .map(nomina -> {
                    NominaDTO dto = convertirADTO(nomina);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Filtra nóminas por múltiples criterios
     * @param nombre Nombre o parte del nombre del empleado
     * @param departamento Nombre del departamento
     * @param fecha Fecha de la nómina
     * @return Lista de nóminas que cumplen los criterios
     */
    public List<Nomina> filtrarPorNomina(String nombre, String departamento, LocalDate fecha) {
        return nominaRepository.filtroNomina(nombre, departamento, fecha);
    }

    /**
     * Genera un PDF con la última nómina de un empleado
     * @param empleadoId ID del empleado
     * @return Array de bytes con el PDF generado
     */
    public byte[] generarUltimaNominaPdf(Long empleadoId) {
        // Obtiene el empleado
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Obtiene sus nóminas
        List<Nomina> nominas = empleado.getNominas();

        if (nominas == null || nominas.isEmpty()) {
            throw new RuntimeException("El empleado no tiene nóminas");
        }

        // Ordena por fecha descendente
        nominas.sort(Comparator.comparing(Nomina::getFecha).reversed());

        // Genera PDF con la más reciente
        return crearPdfNomina(empleado, nominas.get(0));
    }

    /**
     * Crea el PDF de una nómina
     * @param empleado Empleado dueño de la nómina
     * @param nomina Nómina a imprimir
     * @return Array de bytes con el PDF generado
     */
    private byte[] crearPdfNomina(Empleado empleado, Nomina nomina) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Configuración del documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Intento de carga de logo
            String imageResourcePath = "static/img/OP.jpg";
            URL imageUrl = getClass().getClassLoader().getResource(imageResourcePath);

            if (imageUrl != null) {
                Image logo = Image.getInstance(imageUrl);
                logo.scaleToFit(100f, 50f); // Escala la imagen
                document.add(logo); // Añade al documento
            } else {
                // Mensaje de error si no encuentra el logo
                document.add(new Paragraph("[Logo no encontrado: " + imageResourcePath + "]",
                        new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.RED)));
            }

            // Encabezado del documento
            Paragraph header = new Paragraph("NÓMINA",
                    new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            document.add(new Paragraph(" ")); // Espacio

            // Información del empleado
            document.add(new Paragraph("Empleado: " + empleado.getNombre() + " " + empleado.getApellido()));
            document.add(new Paragraph("Fecha de nómina: " + nomina.getFecha()));
            document.add(new Paragraph(" "));

            // Tabla de líneas de nómina
            PdfPTable table = new PdfPTable(3); // 3 columnas
            table.setWidthPercentage(100); // Ancho completo

            // Encabezados de tabla
            table.addCell(new Phrase("Concepto",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            table.addCell(new Phrase("Tipo",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            table.addCell(new Phrase("Importe",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

            // Rellenado de la tabla con líneas
            BigDecimal total = BigDecimal.ZERO;
            for (LineaNomina linea : nomina.getLineas()) {
                table.addCell(linea.getDescripcion());
                table.addCell(linea.getEsDevengo() ? "Devengo" : "Deducción");
                table.addCell(linea.getImporte().toString() + " €");
                total = total.add(linea.getImporte()); // Acumula el total
            }

            document.add(table);
            document.add(new Paragraph(" "));

            // Total de la nómina
            Paragraph totalParagraph = new Paragraph("Total: " + total.toString() + " €",
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraph);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    /**
     * Carga nóminas de prueba en el sistema
     */
    public void cargarNominas() {
        // Obtiene todos los empleados
        List<Empleado> empleados = empleadoRepository.findAll();

        // Crea una nómina para cada empleado
        List<Nomina> nominas = empleados.stream().map(empleado -> {
            BigDecimal salarioBase = empleado.getSalarioAnual();

            // Crea la nómina
            Nomina nomina = new Nomina();
            nomina.setFecha(LocalDate.now());
            nomina.setEmpleado(empleado);

            // Línea 1: Salario base
            LineaNomina salarioBaseLinea = new LineaNomina();
            salarioBaseLinea.setDescripcion("Salario Base");
            salarioBaseLinea.setPorcentaje(new BigDecimal("22"));
            salarioBaseLinea.setEsDevengo(true);

            BigDecimal importeSalario = salarioBase.multiply(new BigDecimal("22"))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            salarioBaseLinea.setImporte(importeSalario);
            salarioBaseLinea.setNomina(nomina);
            nomina.agregarLinea(salarioBaseLinea);

            // Línea 2: Bonificación
            LineaNomina bonificacion = new LineaNomina();
            bonificacion.setDescripcion("bonificacion por horas extra");
            bonificacion.setPorcentaje(new BigDecimal("12"));
            bonificacion.setEsDevengo(true);

            BigDecimal importeBonificacion = salarioBase.multiply(new BigDecimal("12.00"))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            bonificacion.setImporte(importeBonificacion);
            bonificacion.setNomina(nomina);
            nomina.agregarLinea(bonificacion);

            // Línea 3: Deducción
            LineaNomina devoluciones = new LineaNomina();
            devoluciones.setDescripcion("Devolución");
            devoluciones.setPorcentaje(new BigDecimal("8"));
            devoluciones.setEsDevengo(false);

            BigDecimal importeDevolucion = salarioBase.multiply(new BigDecimal("8"))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            devoluciones.setImporte(importeDevolucion);
            devoluciones.setNomina(nomina);
            nomina.agregarLinea(devoluciones);

            return nomina;
        }).collect(Collectors.toList());

        // Guarda todas las nóminas
        nominaRepository.saveAll(nominas);
        System.out.println("nominas guardadas, supuestamente");
    }

    /**
     * Calcula el importe basado en un porcentaje del salario base
     * @param salarioBase Salario base del empleado
     * @param porcentaje Porcentaje a aplicar
     * @return Importe calculado
     */
    public BigDecimal calcularImportePorcentaje(BigDecimal salarioBase, BigDecimal porcentaje) {
        return salarioBase.multiply(new BigDecimal(porcentaje.doubleValue()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}