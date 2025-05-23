package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.UsuarioDTO;
import grupo.a.modulocomun.DTO.filtros.EmpleadoEditarDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.Direccion;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import grupo.a.modulocomun.Entidades.DatosBancarios;
import grupo.a.modulocomun.Entidades.Departamento;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Maestros.*;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Repositorios.DepartamentoRepository;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import grupo.a.modulocomun.Repositorios.Maestros.*;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import grupo.a.modulocomun.Servicios.Maestros.TipoTarjetaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private UsuaroServiceComun usuarioService;
    @Autowired
    private final DepartamentoService departamentoService;

    @Autowired
    private final RepositoryManager repositoryManager;

    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository, DepartamentoService departamentoService, RepositoryManager repositoryManager) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.departamentoService = departamentoService;
        this.repositoryManager = repositoryManager;
    }


    /**
     * Método para cargar datos iniciales de empleados
     * @Transactional Asegura que todas las operaciones se ejecuten en una sola transacción
     */
    @Transactional
    public void cargarEmpleado() {
        // Primero carga los departamentos necesarios
        departamentoService.cargarDepartamentos();

        // Empleado 1 - Departamento Administración
        Empleado emp1 = insertarEmpleado1();
        emp1.setDepartamento(repositoryManager.getDepartamentoRepository()
                .findDepartamentoByNombre("Administración")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Administración no encontrado")));
        empleadoRepository.save(emp1); // Persiste el empleado
        emp1.setUsuario(usuarioService.insertarBlock(emp1)); // Asigna usuario bloqueado

        // Empleado 2 - Departamento Tecnología
        Empleado emp2 = insertarEmpleado2();
        emp2.setDepartamento(repositoryManager.getDepartamentoRepository()
                .findDepartamentoByNombre("Tecnología")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Tecnología no encontrado")));
        empleadoRepository.save(emp2);
        emp2.setUsuario(usuarioService.insertarUnBlock(emp2)); // Asigna usuario no bloqueado

        // Empleado 3 - Departamento Tecnología
        Empleado emp3 = insertarEmpleado3();
        emp3.setDepartamento(repositoryManager.getDepartamentoRepository()
                .findDepartamentoByNombre("Tecnología")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Tecnología no encontrado")));
        empleadoRepository.save(emp3);
        emp3.setUsuario(usuarioService.insertarBlock(emp3));

        // Empleado 4 - Departamento Recursos Humanos
        Empleado emp4 = insertarEmpleado4();
        emp4.setDepartamento(repositoryManager.getDepartamentoRepository()
                .findDepartamentoByNombre("Recursos Humanos")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Recursos Humanos no encontrado")));
        empleadoRepository.save(emp4);
        emp4.setUsuario(usuarioService.insertarUnBlock(emp4));
    }

    /**
     * Crea y configura el primer empleado de prueba
     * @return Empleado configurado
     */
    public Empleado insertarEmpleado1(){
        Empleado empleado1 = new Empleado(); // Crea nueva instancia

        // Configuración de datos básicos
        empleado1.setNombre("Laura");
        empleado1.setApellido("Martínez");

        // Configuración de género (búsqueda en repositorio)
        empleado1.setGenero(repositoryManager.getGeneroRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));

        // Configuración de datos personales
        empleado1.setEdad(28);
        empleado1.setFecha_nacimiento("1996-07-23");
        empleado1.setPrefijoTel(34);
        empleado1.setTelefono("612345678");
        empleado1.setOtroTelefono("699112233");
        empleado1.setEmail("laura.martinez@empresa.com");

        // Configuración de dirección
        empleado1.setDireccion(new Direccion(
                repositoryManager.getTipoViaRepository().findById(2L)
                        .orElseThrow(EntityNotFoundException::new),
                "Avenida del Sol", 10, 2, 1, "A", "Sevilla", "41001", "Andalucía"));

        // Configuración de nacionalidad y documento
        empleado1.setPaisNacimiento(repositoryManager.getPaisRepository()
                .findById(2L)
                .orElseThrow(EntityNotFoundException::new));
        empleado1.setTipoDocumento(repositoryManager.getTipoDocumentoRepository()
                .findById(2L)
                .orElseThrow(EntityNotFoundException::new));
        empleado1.setDocumento("98765432Z");

        // Configuración de datos laborales
        empleado1.setAdministrador(false);
        empleado1.setComentarios("Incorporada recientemente al departamento de ventas.");
        empleado1.setSalarioAnual(new BigDecimal("28000.00"));
        empleado1.setComisionAnual(new BigDecimal("1500.00"));
        empleado1.setFecha_alta(LocalDate.of(2023, 6, 1));

        // Asignación de especializaciones
        empleado1.setEspecializaciones(List.of(
                repositoryManager.getEspecialidadesRepository()
                        .findById(2L)
                        .orElseThrow(EntityNotFoundException::new)
        ));

        // Estado del empleado
        empleado1.setActivo(true);
        empleado1.setFecha_alta(LocalDate.now());

        return empleado1;
    }

    /**
     * Crea y configura el segundo empleado de prueba
     * @return Empleado configurado
     */
    public Empleado insertarEmpleado2(){
        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Ana");
        empleado2.setApellido("García");

        empleado2.setGenero(repositoryManager.getGeneroRepository()
                .findById(2L)
                .orElseThrow(EntityNotFoundException::new));
        empleado2.setEdad(30);
        empleado2.setFecha_nacimiento("1995-04-12");
        empleado2.setPrefijoTel(34);
        empleado2.setTelefono("600123456");
        empleado2.setOtroTelefono("699987654");
        empleado2.setEmail("ana.garcia@empresa.com");

        empleado2.setDireccion(new Direccion(repositoryManager.getTipoViaRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new),
                "Calle Mayor",24, 12, 3, "B", "Madrid", "28013", "Madrid"));

        empleado2.setPaisNacimiento(repositoryManager.getPaisRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));
        empleado2.setTipoDocumento(repositoryManager.getTipoDocumentoRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));
        empleado2.setDocumento("12345678A");

        empleado2.setAdministrador(true);
        empleado2.setComentarios("Trabajadora muy eficiente.");
        empleado2.setSalarioAnual(new BigDecimal("35000.00"));
        empleado2.setComisionAnual(new BigDecimal("15000.90"));
        empleado2.setFecha_alta(LocalDate.of(2022, 1, 10));

        empleado2.setEspecializaciones(List.of(
                repositoryManager.getEspecialidadesRepository()
                        .findById(1L)
                        .orElseThrow(EntityNotFoundException::new),
                repositoryManager.getEspecialidadesRepository()
                        .findById(4L)
                        .orElseThrow(EntityNotFoundException::new)
        ));
        empleado2.setActivo(true);
        empleado2.setFecha_alta(LocalDate.now());

        return empleado2;
    }

    /**
     * Crea y configura el tercer empleado de prueba
     * @return Empleado configurado
     */
    public Empleado insertarEmpleado3(){
        Empleado empleado3 = new Empleado();
        empleado3.setNombre("Carlos");
        empleado3.setApellido("López");

        empleado3.setGenero(repositoryManager.getGeneroRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));
        empleado3.setEdad(40);
        empleado3.setFecha_nacimiento("1984-02-15");
        empleado3.setPrefijoTel(34);
        empleado3.setTelefono("622334455");
        empleado3.setOtroTelefono("688554433");
        empleado3.setEmail("carlos.lopez@empresa.com");

        empleado3.setDireccion(new Direccion(repositoryManager.getTipoViaRepository()
                .findById(3L)
                .orElseThrow(EntityNotFoundException::new),
                "Camino Real", 45, 0, 0, "", "Valencia", "46001", "Comunidad Valenciana"));

        empleado3.setPaisNacimiento(repositoryManager.getPaisRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));
        empleado3.setTipoDocumento(repositoryManager.getTipoDocumentoRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));
        empleado3.setDocumento("11223344B");

        empleado3.setAdministrador(true);
        empleado3.setComentarios("Responsable del área de IT.");
        empleado3.setSalarioAnual(new BigDecimal("5000.10"));
        empleado3.setComisionAnual(new BigDecimal("15000.00"));
        empleado3.setFecha_alta(LocalDate.of(2021, 3, 15));

        empleado3.setEspecializaciones(List.of(
                repositoryManager.getEspecialidadesRepository()
                        .findById(3L)
                        .orElseThrow(EntityNotFoundException::new),
                repositoryManager.getEspecialidadesRepository()
                        .findById(5L)
                        .orElseThrow(EntityNotFoundException::new)
        ));
        empleado3.setActivo(true);
        empleado3.setFecha_alta(LocalDate.now());

        return empleado3;
    }

    /**
     * Crea y configura el cuarto empleado de prueba
     * @return Empleado configurado
     */
    public Empleado insertarEmpleado4(){
        Empleado empleado4 = new Empleado();
        empleado4.setNombre("Elena");
        empleado4.setApellido("Ríos");

        empleado4.setGenero(repositoryManager.getGeneroRepository()
                .findById(2L)
                .orElseThrow(EntityNotFoundException::new));
        empleado4.setEdad(35);
        empleado4.setFecha_nacimiento("1989-09-10");
        empleado4.setPrefijoTel(34);
        empleado4.setTelefono("633445566");
        empleado4.setOtroTelefono("699776655");
        empleado4.setEmail("elena.rios@empresa.com");

        empleado4.setDireccion(new Direccion(repositoryManager.getTipoViaRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new),
                "Calle Luna", 5, 1, 1, "C", "Barcelona", "08001", "Cataluña"));

        empleado4.setPaisNacimiento(repositoryManager.getPaisRepository()
                .findById(3L)
                .orElseThrow(EntityNotFoundException::new));
        empleado4.setTipoDocumento(repositoryManager.getTipoDocumentoRepository()
                .findById(1L)
                .orElseThrow(EntityNotFoundException::new));
        empleado4.setDocumento("44332211C");

        empleado4.setAdministrador(false);
        empleado4.setComentarios("Experta en atención al cliente y gestión de incidencias.");
        empleado4.setSalarioAnual(new BigDecimal("35000.00"));
        empleado4.setComisionAnual(new BigDecimal("5000.00"));
        empleado4.setFecha_alta(LocalDate.of(2020, 11, 20));

        empleado4.setEspecializaciones(List.of(
                repositoryManager.getEspecialidadesRepository()
                        .findById(2L)
                        .orElseThrow(EntityNotFoundException::new),
                repositoryManager.getEspecialidadesRepository()
                        .findById(5L)
                        .orElseThrow(EntityNotFoundException::new)
        ));
        empleado4.setActivo(false);
        empleado4.setFecha_alta(LocalDate.now());

        return empleado4;
    }

    /**
     * Obtiene un empleado por su ID
     * @param id Identificador del empleado
     * @return Optional que puede contener al empleado o estar vacío
     */
    public Optional<Empleado> obtenerEmpleadoPorId(Long id){
        return empleadoRepository.findById(id); // Consulta al repositorio
    }

    /**
     * Guarda un nuevo empleado en el sistema asociado a un usuario
     * @param empleadoDTO Datos del empleado a guardar
     * @param emailUsuario Email del usuario asociado
     */
    public void guardarEmpleado(EmpleadoDTO empleadoDTO, String emailUsuario) {
        // Busca el usuario por email
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(emailUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado con email: " + emailUsuario);
        }
        Usuario usuario = usuarioOptional.get();

        // Crea el nuevo empleado
        Empleado empleado = new Empleado();

        // Asigna los campos básicos
        asignarCamposSimples(empleado, empleadoDTO);

        // Asigna las relaciones complejas
        asignarGenero(empleado, empleadoDTO);
        asignarDireccion(empleado, empleadoDTO);
        asignarPaisNacimiento(empleado, empleadoDTO);
        asignarDatosBancarios(empleado, empleadoDTO);
        asignarEspecialidades(empleado, empleadoDTO);
        asignarDepartamento(empleado, empleadoDTO);
        asignarTipoDocumento(empleado, empleadoDTO);

        // Configura estado y fecha
        empleado.setActivo(true);
        empleado.setFecha_alta(LocalDate.now());

        // Establece la relación con el usuario
        empleado.setUsuario(usuario);

        // Guarda el empleado
        empleadoRepository.save(empleado);

        // Actualiza la relación inversa en el usuario
        usuario.setEmpleado(empleado);
        usuarioRepository.save(usuario);
    }

    /**
     * Asigna los campos simples del DTO al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    private void asignarCamposSimples(Empleado empleado, EmpleadoDTO empleadoDTO) {
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEdad(empleadoDTO.getEdad());
        empleado.setFecha_nacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setDocumento(empleadoDTO.getDocumento());
        empleado.setPrefijoTel(empleadoDTO.getPrefijoTel());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setOtroTelefono(empleadoDTO.getOtroTelefono());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setComentarios(empleadoDTO.getComentarios());

        // Manejo de salario y comisión
        empleado.setSalarioAnual(new BigDecimal(empleadoDTO.getSalarioAnual()));
        if(empleadoDTO.getComisionAnual().isEmpty()){
            empleado.setComisionAnual(BigDecimal.ZERO);
        }else{
            empleado.setComisionAnual(new BigDecimal(empleadoDTO.getComisionAnual()));
        }

        // Manejo de imagen en base64
        empleado.setImagenBase64(empleadoDTO.getImagen());
    }

    /**
     * Asigna el género al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    private void asignarGenero(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Genero genero = repositoryManager.getGeneroRepository()
                .findById(empleadoDTO.getGenero())
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado"));
        empleado.setGenero(genero);
    }

    /**
     * Asigna la dirección al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    private void asignarDireccion(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Direccion direccion = new Direccion();

        // Configura el tipo de vía
        TipoVia tipoVia = repositoryManager.getTipoViaRepository()
                .findById(empleadoDTO.getDireccion().getTipoVia())
                .orElseThrow(() -> new EntityNotFoundException("Tipo Via no encontrado"));
        direccion.setTipoVia(tipoVia);

        // Configura los demás campos de dirección
        direccion.setNombreVia(empleadoDTO.getDireccion().getNombreVia());
        direccion.setNumero(empleadoDTO.getDireccion().getNumero());
        direccion.setPortal(empleadoDTO.getDireccion().getPortal());
        direccion.setPlanta(empleadoDTO.getDireccion().getPlanta());
        direccion.setPuerta(empleadoDTO.getDireccion().getPuerta());
        direccion.setLocalidad(empleadoDTO.getDireccion().getLocalidad());
        direccion.setRegion(empleadoDTO.getDireccion().getRegion());
        direccion.setCod_postal(empleadoDTO.getDireccion().getCod_postal());

        empleado.setDireccion(direccion);
    }

    /**
     * Asigna el país de nacimiento al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    public void asignarPaisNacimiento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Pais paisNacimiento = repositoryManager.getPaisRepository()
                .findById(empleadoDTO.getPaisNacimiento())
                .orElseThrow(() -> new EntityNotFoundException("Pais de nacimiento no encontrado"));
        empleado.setPaisNacimiento(paisNacimiento);
    }

    /**
     * Asigna los datos bancarios al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    public void asignarDatosBancarios(Empleado empleado, EmpleadoDTO empleadoDTO) {
        DatosBancarios datosBancarios = new DatosBancarios();

        // Configuración de tarjeta de crédito
        TarjetaCredito tarjetaCredito = new TarjetaCredito();
        TipoTarjeta tipoTarjeta = repositoryManager.getTipoTarjetaRepository()
                .findById(empleadoDTO.getDatosBancarios().getTarjeta().getTipo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo tarjeta no encontrado"));
        tarjetaCredito.setTipo(tipoTarjeta);
        tarjetaCredito.setNumero(empleadoDTO.getDatosBancarios().getTarjeta().getNumero());
        tarjetaCredito.setCvc(empleadoDTO.getDatosBancarios().getTarjeta().getCvc());

        datosBancarios.setTarjeta(tarjetaCredito);
        datosBancarios.setNumCuenta(empleadoDTO.getDatosBancarios().getNumCuenta());

        // Configuración de entidad bancaria
        EntidadBancaria entidadBancaria = repositoryManager.getEntidadBancariaRepository()
                .findById(empleadoDTO.getDatosBancarios().getEntidadBancaria())
                .orElseThrow(() -> new EntityNotFoundException("Entidad bancaria no encontrado"));
        datosBancarios.setEntidadBancaria(entidadBancaria);

        empleado.setDatosBancarios(datosBancarios);
    }

    /**
     * Asigna las especialidades al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    public void asignarEspecialidades(Empleado empleado, EmpleadoDTO empleadoDTO) {
        // Mapea los IDs de especialidades a entidades
        List<Especialidades> especializaciones = empleadoDTO.getEspecializaciones()
                .stream()
                .map(id -> repositoryManager.getEspecialidadesRepository().findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Especialidad no encontrada")))
                .collect(Collectors.toList());

        empleado.setEspecializaciones(especializaciones);
    }

    /**
     * Asigna el departamento al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    public void asignarDepartamento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Departamento departamento = repositoryManager.getDepartamentoRepository()
                .findById(empleadoDTO.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));
        empleado.setDepartamento(departamento);
    }

    /**
     * Asigna el tipo de documento al empleado
     * @param empleado Entidad empleado
     * @param empleadoDTO DTO con los datos
     */
    public void asignarTipoDocumento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        TipoDocumento tipoDocumento = repositoryManager.getTipoDocumentoRepository()
                .findById(empleadoDTO.getTipoDocumento())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
        empleado.setTipoDocumento(tipoDocumento);
    }

    /**
     * Obtiene todos los empleados del sistema
     * @return Lista de todos los empleados
     */
    public List<Empleado> obtenerTodosEmpleados() {
        return empleadoRepository.findAll();
    }

    /**
     * Convierte una entidad Empleado a su representación DTO
     * @param empleado Entidad a convertir
     * @return DTO con los datos del empleado
     */
    public EmpleadoDTO convertirEmpleadoADTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();

        // Mapeo de campos básicos
        dto.setId_empleado(empleado.getId_empleado());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getPrefijoTel() + " " + empleado.getTelefono());
        dto.setFechaNacimiento(empleado.getFecha_nacimiento());
        dto.setSalarioAnual(empleado.getSalarioAnual().toString());
        dto.setComisionAnual(empleado.getComisionAnual().toString());
        dto.setComentarios(empleado.getComentarios());
        dto.setActivo(empleado.isActivo());
        dto.setFechaBaja(empleado.getFechaBaja());

        // Mapeo de departamento si existe
        if(empleado.getDepartamento() != null) {
            dto.setIdDepartamento(empleado.getDepartamento().getId_dept());
            dto.setDepartamento(departamentoService.convertirDTO(empleado.getDepartamento()));
        }

        // Mapeo de usuario si existe
        if(empleado.getUsuario() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId_usuario(empleado.getUsuario().getId_usuario());
            usuarioDTO.setEmail(empleado.getUsuario().getEmail());
            usuarioDTO.setBloqueado(empleado.getUsuario().isBloqueado());
            usuarioDTO.setMotivoBloqueo(empleado.getUsuario().getMotivoBloqueo());
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }

    /**
     * Convierte una entidad Empleado a DTO para edición
     * @param empleado Entidad a convertir
     * @return DTO editable con los datos del empleado
     */
    public EmpleadoEditarDTO convertirEmpleadoEditableADTO(Empleado empleado) {
        EmpleadoEditarDTO dto = new EmpleadoEditarDTO();

        // Mapeo de campos básicos
        dto.setId_empleado(empleado.getId_empleado());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getPrefijoTel() + " " + empleado.getTelefono());
        dto.setFechaNacimiento(empleado.getFecha_nacimiento());
        dto.setSalarioAnual(empleado.getSalarioAnual().toString());
        dto.setComisionAnual(empleado.getComisionAnual().toString());
        dto.setComentarios(empleado.getComentarios());

        // Mapeo de departamento si existe
        if(empleado.getDepartamento() != null) {
            dto.setIdDepartamento(empleado.getDepartamento().getId_dept());
            dto.setDepartamento(departamentoService.convertirDTO(empleado.getDepartamento()));
        }

        // Mapeo de usuario si existe
        if(empleado.getUsuario() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId_usuario(empleado.getUsuario().getId_usuario());
            usuarioDTO.setEmail(empleado.getUsuario().getEmail());
            usuarioDTO.setBloqueado(empleado.getUsuario().isBloqueado());
            usuarioDTO.setMotivoBloqueo(empleado.getUsuario().getMotivoBloqueo());
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }

    /**
     * Obtiene un empleado por su correo electrónico
     * @param correo Correo a buscar
     * @return Optional con el empleado encontrado o vacío
     */
    public Optional<Empleado> obtenerEmpleadoPorCorreo(String correo) {
        return empleadoRepository.findByEmail(correo);
    }

    /**
     * Obtiene la imagen en base64 de un empleado por su correo
     * @param correo Correo del empleado
     * @return String con la imagen en base64 o null si no existe
     */
    public String obtenerImagenBase64PorCorreo(String correo) {
        Optional<Empleado> empleadoOpt = obtenerEmpleadoPorCorreo(correo);

        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            if (empleado.getImagenBase64() != null && !empleado.getImagenBase64().isEmpty()) {
                return empleado.getImagenBase64();
            }
        }
        return null;
    }

    /**
     * Obtiene los nombres de todos los departamentos
     * @return Lista de nombres de departamentos
     */
    public List<String> obtenerNombresDepartamentos() {
        return repositoryManager.getDepartamentoRepository().findDistinctNombresDepartamento();
    }

    /**
     * Busca empleados aplicando múltiples filtros
     * @param nombre Nombre o parte del nombre a buscar
     * @param departamentos Lista de nombres de departamentos para filtrar
     * @param salarioMin Salario mínimo
     * @param salarioMax Salario máximo
     * @return Lista de empleados que cumplen con los filtros
     */
    public List<Empleado> buscarFiltrados(String nombre, List<String> departamentos, BigDecimal salarioMin, BigDecimal salarioMax) {
        // Si no hay filtros, devuelve todos los empleados
        if ((nombre == null || nombre.trim().isEmpty()) &&
                (departamentos == null || departamentos.isEmpty()) &&
                salarioMin == null && salarioMax == null) {
            return empleadoRepository.findAll();
        }

        // Convierte los nombres de departamentos a minúsculas para búsqueda case-insensitive
        List<String> departamentosLower = departamentos != null && !departamentos.isEmpty() ?
                departamentos.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()) :
                null;

        // Establece valores por defecto para los salarios
        BigDecimal minSalario = salarioMin != null ? salarioMin : BigDecimal.ZERO;
        BigDecimal maxSalario = salarioMax != null ? salarioMax : new BigDecimal("999999999.99");

        // Combinaciones de filtros posibles
        if (nombre != null && !nombre.trim().isEmpty()) {
            if (departamentosLower != null && !departamentosLower.isEmpty()) {
                if (salarioMin != null || salarioMax != null) {
                    // Filtro por nombre, departamento y salario
                    return empleadoRepository.findByNombreContainingIgnoreCaseAndDepartamentoNombredeptInAndSalarioAnualBetween(
                            nombre, departamentosLower, minSalario, maxSalario);
                } else {
                    // Filtro por nombre y departamento
                    return empleadoRepository.findByNombreContainingIgnoreCaseAndDepartamentoNombredeptIn(
                            nombre, departamentosLower);
                }
            } else if (salarioMin != null || salarioMax != null) {
                // Filtro por nombre y salario
                return empleadoRepository.findByNombreContainingIgnoreCaseAndSalarioAnualBetween(
                        nombre, minSalario, maxSalario);
            } else {
                // Filtro solo por nombre
                return empleadoRepository.findByNombreContainingIgnoreCase(nombre);
            }
        } else if (departamentosLower != null && !departamentosLower.isEmpty()) {
            if (salarioMin != null || salarioMax != null) {
                // Filtro por departamento y salario
                return empleadoRepository.findByDepartamentoNombredeptInAndSalarioAnualBetween(
                        departamentosLower, minSalario, maxSalario);
            } else {
                // Filtro solo por departamento
                return empleadoRepository.findByDepartamentoNombredeptIn(departamentosLower);
            }
        } else if (salarioMin != null || salarioMax != null) {
            // Filtro solo por salario
            return empleadoRepository.findBySalarioAnualBetween(minSalario, maxSalario);
        }

        // Si no se aplicó ningún filtro válido, devuelve todos
        return empleadoRepository.findAll();
    }


    public EmpleadoDTO obtenerEmpleadoDTOPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return convertirEmpleadoADTO(empleado);
    }

    public EmpleadoEditarDTO obtenerEmpleadoEditableDTOPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return convertirEmpleadoEditableADTO(empleado);
    }

    public List<Genero> obtenerTodosGeneros() {
        return repositoryManager.getGeneroRepository().findAll();
    }

    public List<Pais> obtenerTodosPaises() {
        return repositoryManager.getPaisRepository().findAll();
    }

    public List<TipoDocumento> obtenerTodosTiposDocumento() {
        return repositoryManager.getTipoDocumentoRepository().findAll();
    }

    public List<Departamento> obtenerTodosDepartamentos() {
        return repositoryManager.getDepartamentoRepository().findAll();
    }

    public List<Especialidades> obtenerTodasEspecialidades() {
        return repositoryManager.getEspecialidadesRepository().findAll();
    }
    @Transactional
    public void actualizarEmpleadoCompleto(Long id, EmpleadoEditarDTO empleadoDTO) {
        // 1. Obtener el empleado existente
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID: " + id));

        // 2. Validar datos básicos
        if (empleadoDTO == null) {
            throw new IllegalArgumentException("Datos del empleado no pueden ser nulos");
        }

        // 3. Actualizar campos básicos
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setOtroTelefono(empleadoDTO.getOtroTelefono());
        empleado.setComentarios(empleadoDTO.getComentarios());

        // 4. Actualizar campos numéricos con validación
        try {
            empleado.setSalarioAnual(new BigDecimal(empleadoDTO.getSalarioAnual()));
            empleado.setComisionAnual(empleadoDTO.getComisionAnual() != null && !empleadoDTO.getComisionAnual().isEmpty() ?
                    new BigDecimal(empleadoDTO.getComisionAnual()) : BigDecimal.ZERO);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de salario o comisión inválido");
        }

        // 5. Actualizar relaciones
        if (empleadoDTO.getGenero() != null) {
            Genero genero = repositoryManager.getGeneroRepository()
                    .findById(empleadoDTO.getGenero())
                    .orElseThrow(() -> new EntityNotFoundException("Género no encontrado"));
            empleado.setGenero(genero);
        }

        // 6. Actualizar departamento
        if (empleadoDTO.getIdDepartamento() != null) {
            Departamento departamento = repositoryManager.getDepartamentoRepository()
                    .findById(empleadoDTO.getIdDepartamento())
                    .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));
            empleado.setDepartamento(departamento);
        }

        // 7. Actualizar especializaciones
        if (empleadoDTO.getEspecializaciones() != null && !empleadoDTO.getEspecializaciones().isEmpty()) {
            List<Especialidades> especialidades = empleadoDTO.getEspecializaciones().stream()
                    .map(especialidadId -> repositoryManager.getEspecialidadesRepository().findById(especialidadId)
                            .orElseThrow(() -> new EntityNotFoundException("Especialidad no encontrada")))
                    .collect(Collectors.toList());
            empleado.setEspecializaciones(especialidades);
        }

        // 8. Actualizar imagen si se proporcionó
        if (empleadoDTO.getImagen() != null && !empleadoDTO.getImagen().isEmpty()) {
            empleado.setImagenBase64(empleadoDTO.getImagen());
        }

        // 9. Guardar los cambios
        empleadoRepository.save(empleado);
    }

    @Transactional
    public void darDeBaja(Long idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        empleado.setActivo(false);
        empleado.setFechaBaja(LocalDate.now());
        empleadoRepository.save(empleado);

        // Opcional: También puedes desactivar el usuario asociado
        if(empleado.getUsuario() != null) {
            empleado.getUsuario().setBloqueado(true);
            empleado.getUsuario().setMotivoBloqueo("Empleado dado de baja");
            usuarioRepository.save(empleado.getUsuario());
        }
    }

    @Transactional
    public void recuperarEmpleado(Long idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        empleado.setActivo(true);
        empleado.setFechaBaja(null);
        empleadoRepository.save(empleado);

        // Opcional: Reactivar el usuario asociado
        if(empleado.getUsuario() != null) {
            empleado.getUsuario().setBloqueado(false);
            empleado.getUsuario().setMotivoBloqueo(null);
            usuarioRepository.save(empleado.getUsuario());
        }
    }

    public List<Empleado> obtenerEmpleadosInactivos() {
        return empleadoRepository.findByActivoFalse();
    }
    public Empleado cargarEmpleadoConNominas(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .map(empleado -> {
                    // Inicializar la colección nominas dentro de la sesión
                    Hibernate.initialize(empleado.getNominas());
                    return empleado;
                })
                .orElse(null);
    }

}



//    public void validarMaestrosPaso1(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
//        if (empleadoDTO.getGenero() == null) {
//            bindingResult.rejectValue("genero", "validation.notNull");
//        } else if (!repositoryManager.getGeneroRepository().existsById(empleadoDTO.getGenero())) {
//            bindingResult.rejectValue("genero", "valor.invalido");
//        }
//
//        if (!repositoryManager.getPaisRepository().existsById(empleadoDTO.getPaisNacimiento())) {
//            bindingResult.rejectValue("paisNacimiento", "valor.invalido");
//        }
//    }
//    public void validarMaestrosPaso2(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
//        if (empleadoDTO.getTipoDocumento() == null) {
//            bindingResult.rejectValue("tipoDocumento", "validation.notNull");
//        } else if (!repositoryManager.getTipoDocumentoRepository().existsById(empleadoDTO.getTipoDocumento())) {
//            bindingResult.rejectValue("tipoDocumento", "valor.invalido");
//        }
//
//        if (!repositoryManager.getTipoViaRepository().existsById(empleadoDTO.getDireccion().getTipoVia())) {
//            bindingResult.rejectValue("direccion.tipoVia", "valor.invalido");
//        }
//    }

//    public void validarMaestrosPaso3(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
//            if (!repositoryManager.getDepartamentoRepository().existsById(empleadoDTO.getIdDepartamento())) {
//            bindingResult.rejectValue("idDepartamento", "valor.invalido");
//        }
//
//        if (empleadoDTO.getEspecializaciones() == null || empleadoDTO.getEspecializaciones().isEmpty()) {
//            bindingResult.rejectValue("especializaciones", "validation.notNull");
//        } else if (
//                //usamos un stream para que devuelva el primer error de id que encuentre
//                empleadoDTO.getEspecializaciones().stream()
//            .anyMatch(id -> !repositoryManager.getEspecialidadesRepository().existsById(id))
//        ){
//            bindingResult.rejectValue("especializaciones", "valor.invalido");
//        }
//    }
//    public void validarMaestrosPaso4(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
//            if (!repositoryManager.getEntidadBancariaRepository().existsById(empleadoDTO.getDatosBancarios().getEntidadBancaria())) {
//            bindingResult.rejectValue("datosBancarios.entidadBancaria", "valor.invalido");
//        }
//
//        if (!repositoryManager.getTipoTarjetaRepository().existsById(empleadoDTO.getDatosBancarios().getTarjeta().getTipo())) {
//            bindingResult.rejectValue("datosBancarios.tarjeta.tipo", "valor.invalido");
//        }
//    }