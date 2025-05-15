package grupo.a.modulocomun.Servicios;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.LineaNominaDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.DTO.filtros.filtrosNominasDTO;
import grupo.a.modulocomun.Entidades.*;
import grupo.a.modulocomun.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
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

        // Obtener salario base anual del empleado
        BigDecimal salarioAnual = empleado.getSalarioAnual();

        for (LineaNominaDTO lineaDTO : nominaDTO.getLineas()) {
            if (lineaDTO.getConcepto() == null || lineaDTO.getConcepto().trim().isEmpty()) {
                throw new RuntimeException("El concepto de la línea no puede estar vacío");
            }

            if (lineaDTO.getEsDevengo() == null) {
                throw new RuntimeException("Debe especificar si la línea es devengo o deducción");
            }

            LineaNomina linea = new LineaNomina();
            linea.setDescripcion(lineaDTO.getConcepto());
            linea.setEsDevengo(lineaDTO.getEsDevengo());

            BigDecimal importe;

            // Calcular importe según porcentaje o importe fijo
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

            // Si es deducción, el importe será negativo
            if (!lineaDTO.getEsDevengo()) {
                importe = importe.negate();
            }

            linea.setImporte(importe);
            linea.setNomina(nomina);
            nomina.getLineas().add(linea);
        }

        // Guardar la nómina
        Nomina nominaGuardada = nominaRepository.save(nomina);
        return convertirADTO(nominaGuardada);
    }


    public List<NominaDTO> obtenerNominasPorEmpleado(Long empleadoId) {
        return nominaRepository.findNominasConLineasByEmpleadoId(empleadoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public filtrosNominasDTO returnConsultaFiltradoNominas (Nomina nomina) {
        filtrosNominasDTO filtrado = new filtrosNominasDTO();
        filtrado.setNombre(nomina.getEmpleado().getNombre());
        filtrado.setId(nomina.getEmpleado().getId_empleado());
        filtrado.setFecha(nomina.getFecha());
        BigDecimal total = nomina.getLineas().stream()
                .map(LineaNomina::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        filtrado.setSalario(total);

        if(nomina.getEmpleado() != null && nomina.getEmpleado().getDepartamento() != null){
            filtrado.setDepartamento(nomina.getEmpleado().getDepartamento().getNombre_dept());
        }else {
            filtrado.setDepartamento("Sin departamento");
        }
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
                    lineaDTO.setEsDevengo(linea.getEsDevengo());

                    if (linea.getPorcentaje() != null) {
                        lineaDTO.setPorcentaje(linea.getPorcentaje().toString());
                    }
                    if (linea.getImporteFijo() != null) {
                        lineaDTO.setImporteFijo(linea.getImporteFijo().toString());
                    }

                    lineaDTO.setCantidad(linea.getImporte() != null ? linea.getImporte().toString() : "0.00");
                    return lineaDTO;
                })
                .collect(Collectors.toList());
        dto.setLineas(lineas);

        // Calcular total (suma de todos los importes, ya vienen con signo)
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



    public byte[] generarUltimaNominaPdf(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        List<Nomina> nominas = empleado.getNominas();

        if (nominas == null || nominas.isEmpty()) {
            throw new RuntimeException("El empleado no tiene nóminas");
        }

        // Ordenar por fecha descendente
        nominas.sort(Comparator.comparing(Nomina::getFecha).reversed());

        return crearPdfNomina(empleado, nominas.get(0)); // Tomar la más reciente
    }

    private byte[] crearPdfNomina(Empleado empleado, Nomina nomina) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Encabezado
            Paragraph header = new Paragraph("NÓMINA", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            document.add(new Paragraph(" ")); // Espacio

            // Información del empleado
            document.add(new Paragraph("Empleado: " + empleado.getNombre() + " " + empleado.getApellido()));
            document.add(new Paragraph("Fecha de nómina: " + nomina.getFecha()));
            document.add(new Paragraph(" "));

            // Tabla de líneas de nómina
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            // Encabezados de tabla
            table.addCell(new Phrase("Concepto", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            table.addCell(new Phrase("Tipo", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            table.addCell(new Phrase("Importe", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

            // Líneas de nómina
            BigDecimal total = BigDecimal.ZERO;
            for (LineaNomina linea : nomina.getLineas()) {
                table.addCell(linea.getDescripcion());
                table.addCell(linea.getEsDevengo() ? "Devengo" : "Deducción");
                table.addCell(linea.getImporte().toString() + " €");
                total = total.add(linea.getImporte());
            }

            document.add(table);
            document.add(new Paragraph(" "));

            // Total
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

    public void cargarNominas() {
            List<Empleado> empleados = empleadoRepository.findAll();
            List<Nomina> nominas = empleados.stream().map(empleado -> {
                Nomina nomina = new Nomina();
                nomina.setFecha(LocalDate.now());
                nomina.setEmpleado(empleado);

                // Crear líneas de nómina (ajusta los importes y las descripciones según sea necesario)
                LineaNomina linea1 = new LineaNomina("Salario Base", new BigDecimal("1500.00"));
                LineaNomina linea2 = new LineaNomina("Bonificación", new BigDecimal("300.00"));
                LineaNomina linea3 = new LineaNomina("Horas Extra", new BigDecimal("200.00"));

                // Asociar las líneas de nómina con la nómina
                nomina.agregarLinea(linea1);
                nomina.agregarLinea(linea2);
                nomina.agregarLinea(linea3);

                return nomina;
            }).collect(Collectors.toList());

            nominaRepository.saveAll(nominas);
        System.out.println("nominas guardadas, supuestamente");
    }
}