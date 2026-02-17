package com.quizapp.controller.web;

import com.quizapp.model.entity.Usuario;
import com.quizapp.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /registro — mostrar formulario
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // POST /registro — procesar formulario
    @PostMapping("/registro")
    public String procesarRegistro(
            @ModelAttribute Usuario usuario,
            @RequestParam String confirmarPassword,
            Model model,
            HttpServletRequest request) {

        if (!usuario.getPassword().equals(confirmarPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        if (usuarioService.existeUsername(usuario.getUsername())) {
            model.addAttribute("error", "El usuario ya existe");
            return "registro";
        }

        if (usuarioService.existeEmail(usuario.getEmail())) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        // Registrar usuario
        usuarioService.registrar(usuario);

        // Login automático después del registro
        try {
            request.login(usuario.getUsername(), usuario.getPassword());
        } catch (Exception e) {
            return "redirect:/login?registrado=true";
        }

        return "redirect:/";
    }
}