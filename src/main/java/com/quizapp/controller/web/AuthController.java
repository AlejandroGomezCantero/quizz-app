package com.quizapp.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.model.entity.Usuario;
import com.quizapp.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    // POST /login
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Usuario usuario = usuarioService.findByUsername(username).orElse(null);
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/login";
        }
        
        if (!usuarioService.validarPassword(password, usuario.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Contraseña incorrecta");
            return "redirect:/login";
        }
        
        if (!usuario.isActivo()) {
            redirectAttributes.addFlashAttribute("error", "Usuario inactivo");
            return "redirect:/login";
        }
        
        // Login exitoso
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("username", usuario.getUsername());
        session.setAttribute("roles", usuario.getRoles());
        
        usuarioService.actualizarUltimoLogin(usuario.getId());
        
        return "redirect:/";
    }

    // GET /registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    // POST /registro
    @PostMapping("/registro")
    public String registro(
            @ModelAttribute Usuario usuario,
            @RequestParam String confirmarPassword,
            RedirectAttributes redirectAttributes) {
        
        // Validaciones
        if (usuarioService.existsByUsername(usuario.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "El username ya existe");
            return "redirect:/registro";
        }
        
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/registro";
        }
        
        if (!usuario.getPassword().equals(confirmarPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro";
        }
        
        // Registrar
        usuarioService.registrar(usuario);
        
        redirectAttributes.addFlashAttribute("exito", 
            "¡Registro exitoso! Ya puedes iniciar sesión");
        
        return "redirect:/login";
    }

    // GET /logout
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("exito", "Sesión cerrada correctamente");
        return "redirect:/login";
    }

    // GET /perfil
    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        Usuario usuario = usuarioService.findByUsername(
            (String) session.getAttribute("username")).orElse(null);
        
        model.addAttribute("usuario", usuario);
        return "auth/perfil";
    }
}