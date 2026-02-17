package com.quizapp.controller.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.model.entity.PreguntaSeleccionUnica;
import com.quizapp.service.PreguntaSeleccionUnicaService;
import com.quizapp.service.CategoriaService;

// Controller web para Selección Única
@Controller
@RequestMapping("/preguntas/seleccion-unica")
public class PreguntaSeleccionUnicaWebController {

    private final PreguntaSeleccionUnicaService preguntaService;
    private final CategoriaService categoriaService;

    public PreguntaSeleccionUnicaWebController(
            PreguntaSeleccionUnicaService preguntaService,
            CategoriaService categoriaService) {
        this.preguntaService = preguntaService;
        this.categoriaService = categoriaService;
    }

    // ========== LISTAR ==========
    
    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String texto,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<PreguntaSeleccionUnica> paginaPreguntas =
            preguntaService.buscarConFiltros(categoriaId, texto, pageable);

        model.addAttribute("preguntas", paginaPreguntas.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaPreguntas.getTotalPages());
        model.addAttribute("totalElementos", paginaPreguntas.getTotalElements());
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("texto", texto);

        return "preguntas/seleccion-unica/listar";
    }
    // ========== NUEVA ==========
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        PreguntaSeleccionUnica pregunta = new PreguntaSeleccionUnica();
        
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Nueva Pregunta Selección Única");
        model.addAttribute("esNueva", true);
        model.addAttribute("categorias", categoriaService.findAll());
        
        return "preguntas/seleccion-unica/formulario";
    }

    // ========== EDITAR ==========
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        PreguntaSeleccionUnica pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Editar Pregunta Selección Única");
        model.addAttribute("esNueva", false);
        model.addAttribute("categorias", categoriaService.findAll());
        
        return "preguntas/seleccion-unica/formulario";
    }

    // ========== GUARDAR ==========
    
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute PreguntaSeleccionUnica pregunta,
            @RequestParam(required = false) Long categoriaId,
            RedirectAttributes redirectAttributes) {
        
        try {
            if (categoriaId != null) {
                categoriaService.findById(categoriaId).ifPresent(pregunta::setCategoria);
            }
            
            preguntaService.save(pregunta);
            
            String mensaje = (pregunta.getId() == null) 
                ? "Pregunta creada con éxito" 
                : "Pregunta actualizada con éxito";
            redirectAttributes.addFlashAttribute("mensajeExito", mensaje);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }
        
        return "redirect:/preguntas/seleccion-unica";
    }

    // ========== VER ==========
    
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        PreguntaSeleccionUnica pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        model.addAttribute("pregunta", pregunta);
        
        return "preguntas/seleccion-unica/ver";
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
        
        return "redirect:/preguntas/seleccion-unica";
    }
}
