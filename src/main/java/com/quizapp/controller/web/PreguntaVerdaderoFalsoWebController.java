package com.quizapp.controller.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.model.entity.PreguntaVerdaderoFalso;
import com.quizapp.service.PreguntaVerdaderoFalsoService;
import com.quizapp.service.CategoriaService;

// Controller web para preguntas de Verdadero/Falso
@Controller
@RequestMapping("/preguntas/vf")
public class PreguntaVerdaderoFalsoWebController {

    private final PreguntaVerdaderoFalsoService preguntaService;
    private final CategoriaService categoriaService;

    public PreguntaVerdaderoFalsoWebController(
            PreguntaVerdaderoFalsoService preguntaService,
            CategoriaService categoriaService) {
        this.preguntaService = preguntaService;
        this.categoriaService = categoriaService;
    }

    // ========== LISTAR PREGUNTAS ==========
    
    // GET /preguntas/vf
    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String texto,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<PreguntaVerdaderoFalso> paginaPreguntas = 
            preguntaService.buscarConFiltros(categoriaId, texto, pageable);

        model.addAttribute("preguntas", paginaPreguntas.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaPreguntas.getTotalPages());
        model.addAttribute("totalElementos", paginaPreguntas.getTotalElements());
        model.addAttribute("categorias", categoriaService.findAll()); // NUEVO
        model.addAttribute("categoriaId", categoriaId); // Para mantener filtro activo
        model.addAttribute("texto", texto); // Para mantener filtro activo

        return "preguntas/vf/listar";
    }
    // ========== MOSTRAR FORMULARIO NUEVA ==========
    
    // GET /preguntas/vf/nueva
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        // Pregunta vacía
        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso();
        
        // Añadir al modelo
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Nueva Pregunta V/F");
        model.addAttribute("esNueva", true);
        
        // Lista de categorías para el <select>
        model.addAttribute("categorias", categoriaService.findAll());
        
        // Devolver vista: templates/preguntas/vf/formulario.html
        return "preguntas/vf/formulario";
    }

    // ========== MOSTRAR FORMULARIO EDITAR ==========
    
    // GET /preguntas/vf/editar/5
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        // Buscar la pregunta
        PreguntaVerdaderoFalso pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        // Añadir al modelo
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Editar Pregunta V/F");
        model.addAttribute("esNueva", false);
        model.addAttribute("categorias", categoriaService.findAll());
        
        return "preguntas/vf/formulario";
    }

    // ========== GUARDAR PREGUNTA ==========
    
    // POST /preguntas/vf/guardar
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute PreguntaVerdaderoFalso pregunta,
            @RequestParam(required = false) Long categoriaId, // ID de la categoría seleccionada
            RedirectAttributes redirectAttributes) {
        
        try {
            // Si se seleccionó una categoría, asignarla
            if (categoriaId != null) {
                categoriaService.findById(categoriaId).ifPresent(pregunta::setCategoria);
            }
            
            // Guardar
            preguntaService.save(pregunta);
            
            String mensaje = (pregunta.getId() == null) 
                ? "Pregunta creada con éxito" 
                : "Pregunta actualizada con éxito";
            redirectAttributes.addFlashAttribute("mensajeExito", mensaje);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }
        
        return "redirect:/preguntas/vf";
    }

    // ========== VER DETALLE ==========
    
    // GET /preguntas/vf/ver/5
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        PreguntaVerdaderoFalso pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        model.addAttribute("pregunta", pregunta);
        
        // Devolver vista: templates/preguntas/vf/ver.html
        return "preguntas/vf/ver";
    }

    // ========== ELIMINAR ==========
    
    // GET /preguntas/vf/eliminar/5
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            preguntaService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pregunta eliminada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al eliminar: " + e.getMessage());
        }
        
        return "redirect:/preguntas/vf";
    }
}