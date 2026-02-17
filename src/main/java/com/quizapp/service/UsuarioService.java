package com.quizapp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.quizapp.model.entity.Usuario;
import com.quizapp.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Registrar nuevo usuario
    public Usuario registrar(Usuario usuario) {
        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Asignar rol por defecto
        usuario.addRole("USER");
        
        // Guardar
        return usuarioRepository.save(usuario);
    }

    // Buscar por username
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Buscar por email
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verificar si existe username
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    // Verificar si existe email
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Validar login
    public boolean validarPassword(String passwordRaw, String passwordEncriptado) {
        return passwordEncoder.matches(passwordRaw, passwordEncriptado);
    }

    // Actualizar último login
    public void actualizarUltimoLogin(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setUltimoLogin(LocalDateTime.now());
            usuarioRepository.save(usuario);
        });
    }

    // Listar todos
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}