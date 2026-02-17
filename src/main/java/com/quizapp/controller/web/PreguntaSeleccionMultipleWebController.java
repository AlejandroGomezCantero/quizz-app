package com.quizapp.controller.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;
import com.quizapp.service.PreguntaSeleccionMultipleService;
import com.quizapp.service.CategoriaService;

import java.util.Arrays;
import java.util.List;

// Controller web para Selección Múltiple
@Controller
@RequestMapping("/preguntas/seleccion-multiple")
public class PreguntaSeleccionMultipleWebController {

    private final PreguntaSeleccionMultipleService preguntaService;
    private final CategoriaService categoriaService;

    public PreguntaSeleccionMultipleWebController(
            PreguntaSeleccionMultipleService preguntaService,
            CategoriaService categoriaService) {
        this.preguntaService = preguntaService;
        this.categoriaService = categoriaService;
    }

    // ========== LISTAR ==========
    
    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, 10);
        Page<PreguntaSeleccionMultiple> paginaPreguntas = preguntaService.findAllPaginated(pageable);
        
        model.addAttribute("preguntas", paginaPreguntas.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaPreguntas.getTotalPages());
        model.addAttribute("totalElementos", paginaPreguntas.getTotalElements());
        
        return "preguntas/seleccion-multiple/listar";
    }

    // ========== NUEVA ==========
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        PreguntaSeleccionMultiple pregunta = new PreguntaSeleccionMultiple();
        
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Nueva Pregunta Selección Múltiple");
        model.addAttribute("esNueva", true);
        model.addAttribute("categorias", categoriaService.findAll());
        
        return "preguntas/seleccion-multiple/formulario";
    }

    // ========== EDITAR ==========
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        PreguntaSeleccionMultiple pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Editar Pregunta Selección Múltiple");
        model.addAttribute("esNueva", false);
        model.addAttribute("categorias", categoriaService.findAll());
        
        return "preguntas/seleccion-multiple/formulario";
    }

    // ========== GUARDAR ==========
    
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute PreguntaSeleccionMultiple pregunta,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) List<String> respuestasCorrectas,
            RedirectAttributes redirectAttributes) {
        
        try {
            if (categoriaId != null) {
                categoriaService.findById(categoriaId).ifPresent(pregunta::setCategoria);
            }
            
            // Asignar respuestas correctas (pueden ser varias: A, B, C, D)
            if (respuestasCorrectas != null && !respuestasCorrectas.isEmpty()) {
                pregunta.setRespuestasCorrectas(respuestasCorrectas);
            }
            
            preguntaService.save(pregunta);
            
            String mensaje = (pregunta.getId() == null) 
                ? "Pregunta creada con éxito" 
                : "Pregunta actualizada con éxito";
            redirectAttributes.addFlashAttribute("mensajeExito", mensaje);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }
        
        return "redirect:/preguntas/seleccion-multiple";
    }

    // ========== VER ==========
    
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        PreguntaSeleccionMultiple pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        model.addAttribute("pregunta", pregunta);
        
        return "preguntas/seleccion-multiple/ver";
    }

    // ========== ELIMINAR ==========
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            preguntaService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pregunta eliminada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }
        
        return "redirect:/preguntas/seleccion-multiple";
    }
}