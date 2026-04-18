package com.sst.registro.config;

import com.sst.registro.model.entity.Usuario;
import com.sst.registro.model.enums.RolUsuario;
import com.sst.registro.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    
    private final UsuarioRepository usuarioRepository;
    
    @PostConstruct
    public void init() {
        try {
            Optional<Usuario> adminExistente = usuarioRepository.findByCorreo("admin@sgsst.com");
            if (adminExistente.isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setCorreo("admin@sgsst.com");
                admin.setPassword("$2a$10$8K1p/a0dL3RXgQYL5W5v5.J9p5VVJR5hZ5V5Q5Y5W5v5VVJR5hZ"); // admin123 encriptado
                admin.setRol(RolUsuario.ADMIN);
                admin.setArea("SST");
                admin.setActivo(true);
                usuarioRepository.save(admin);
                log.info("Usuario admin creado exitosamente");
            }
        } catch (Exception e) {
            log.error("Error al inicializar datos: " + e.getMessage());
        }
    }
}