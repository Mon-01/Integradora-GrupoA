package com.example.aplicacionadministracion.Servicios;



import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Repositorios.UsuarioAdministradorRepository;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Nomina;
import grupo.a.modulocomun.Entidades.UsuarioAdministrador;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import grupo.a.modulocomun.Repositorios.NominaRepository;
import grupo.a.modulocomun.Servicios.NominaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioAdministradorService {

    private final UsuarioAdministradorRepository repository;
    private final EmpleadoRepository empRepository;
    private final PasswordEncoder passwordEncoder;
    private final NominaService nominaService;
    private final NominaRepository nominaRepository;

    @Autowired
    public UsuarioAdministradorService(UsuarioAdministradorRepository repository, PasswordEncoder passwordEncoder, EmpleadoRepository empRepository, NominaService nominaService, NominaRepository nominaRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.empRepository = empRepository;
        this.nominaService = nominaService;
        this.nominaRepository = nominaRepository;
    }

    public Optional<UsuarioAdministrador> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean validarCredenciales(UsuarioAdministradorDTO dto) {
        Optional<UsuarioAdministrador> adminOpt = repository.findByEmail(dto.getEmail());
        return adminOpt.isPresent() && passwordEncoder.matches(dto.getClave(), adminOpt.get().getClave());
    }

    public List<Empleado> obtenerTodosEmpleados() {
        return empRepository.findAll(Sort.by("nombre"));
    }


    public UsuarioAdministrador guardarDesdeDTO(UsuarioAdministradorDTO dto) {
        UsuarioAdministrador admin = new UsuarioAdministrador();
        admin.setEmail(dto.getEmail());
        admin.setClave(passwordEncoder.encode(dto.getClave()));
        return repository.save(admin);
    }


}
