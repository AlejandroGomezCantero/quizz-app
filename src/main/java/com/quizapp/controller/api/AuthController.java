package com.quizapp.controller.api;

import com.quizapp.config.JwtUtil;
import com.quizapp.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {

        // Paso 1: Sacar el usuario y contraseña del cuerpo de la petición
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        // Paso 2: Buscar el usuario en la base de datos
        UserDetails userDetails;
        try {
            userDetails = usuarioService.loadUserByUsername(username);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Usuario no encontrado");
            return ResponseEntity.status(401).body(error);
        }

        // Paso 3: Comprobar que la contraseña es correcta
        boolean passwordCorrecta = passwordEncoder.matches(password, userDetails.getPassword());

        if (!passwordCorrecta) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Contraseña incorrecta");
            return ResponseEntity.status(401).body(error);
        }

        // Paso 4: Generar el token JWT
        String token = jwtUtil.generarToken(username);

        // Paso 5: Devolver el token y el username al frontend
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("username", username);

        return ResponseEntity.ok(respuesta);
    }
}