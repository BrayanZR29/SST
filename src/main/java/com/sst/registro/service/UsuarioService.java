package com.sst.registro.service;

import com.sst.registro.model.entity.Usuario;
import com.sst.registro.model.enums.RolUsuario;
import com.sst.registro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }
    
    @Transactional
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }
    
    public List<Usuario> listarPorRol(RolUsuario rol) {
        return listarTodos().stream()
                .filter(u -> u.getRol() == rol)
                .toList();
    }
    
    @Transactional
    public void crearUsuarioAdmin() {
        if (!existeCorreo("admin@sgsst.com")) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@sgsst.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(RolUsuario.ADMIN);
            admin.setArea("SST");
            usuarioRepository.save(admin);
        }
    }
    
    @Transactional
    public void cambiarPassword(Long usuarioId, String nuevaPassword) {
        Usuario usuario = buscarPorId(usuarioId);
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
    }
}
