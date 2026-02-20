package com.quizapp.controller.web;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;
import com.quizapp.service.CategoriaService;
import com.quizapp.service.PreguntaSeleccionMultipleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/preguntas/seleccion-multiple")
public class PreguntaSeleccionMultipleWebController {

    private final PreguntaSeleccionMultipleService preguntaService;
    private final CategoriaService categoriaService;

    public PreguntaSeleccionMultipleWebController(PreguntaSeleccionMultipleService preguntaService,
                                                   CategoriaService categoriaService) {
        this.preguntaService = preguntaService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(required = false) Long categoriaId,
                         @RequestParam(required = false) String texto,
                         Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<PreguntaSeleccionMultiple> paginaPreguntas = preguntaService.buscarConFiltros(categoriaId, texto, pageable);

        model.addAttribute("preguntas", paginaPreguntas.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaPreguntas.getTotalPages());
        model.addAttribute("totalElementos", paginaPreguntas.getTotalElements());
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("texto", texto);

        return "preguntas/seleccion-multiple/listar";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        PreguntaSeleccionMultiple pregunta = new PreguntaSeleccionMultiple();
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("titulo", "Nueva Pregunta Selección Múltiple");
        model.addAttribute("esNueva", true);
        model.addAttribute("categorias", categoriaService.findAll());
        return "preguntas/seleccion-multiple/formulario";
    }

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

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PreguntaSeleccionMultiple pregunta,
                          @RequestParam(required = false) Long categoriaId,
                          @RequestParam(required = false) List<String> respuestasCorrectas,
                          RedirectAttributes redirectAttributes) {
        try {
            if (categoriaId != null) {
                categoriaService.findById(categoriaId).ifPresent(pregunta::setCategoria);
            }
            if (respuestasCorrectas != null && !respuestasCorrectas.isEmpty()) {
                pregunta.setRespuestasCorrectas(respuestasCorrectas);
            }
            preguntaService.save(pregunta);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pregunta guardada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }
        return "redirect:/preguntas/seleccion-multiple";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        PreguntaSeleccionMultiple pregunta = preguntaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        model.addAttribute("pregunta", pregunta);
        return "preguntas/seleccion-multiple/ver";
    }

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