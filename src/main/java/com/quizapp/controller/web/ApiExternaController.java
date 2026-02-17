package com.quizapp.controller.web;

import com.quizapp.service.ApiExternaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api-externa")
public class ApiExternaController {

    private final ApiExternaService apiExternaService;

    public ApiExternaController(ApiExternaService apiExternaService) {
        this.apiExternaService = apiExternaService;
    }

    // GET /api-externa
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("apiDisponible", apiExternaService.isApiDisponible());
        model.addAttribute("estadisticas", "Open Trivia Database — miles de preguntas disponibles");
        return "preguntas/api-externa/index";
    }

    // POST /api-externa/importar
    @PostMapping("/importar")
    public String importar(
            @RequestParam String tipo,
            @RequestParam int cantidad,
            @RequestParam String categoria,
            RedirectAttributes redirectAttributes) {

        try {
            int importadas = apiExternaService.importarPreguntas(tipo, cantidad, categoria);
            redirectAttributes.addFlashAttribute("exito",
                "✅ Se importaron " + importadas + " preguntas correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                "❌ Error: " + e.getMessage());
        }

        return "redirect:/api-externa";
    }
}