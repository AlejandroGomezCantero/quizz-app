package com.quizapp.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quizapp.service.PreguntaVerdaderoFalsoService;
import com.quizapp.service.PreguntaSeleccionUnicaService;
import com.quizapp.service.PreguntaSeleccionMultipleService;

// Controller para la página general de gestión
@Controller
public class GestionController {

    private final PreguntaVerdaderoFalsoService vfService;
    private final PreguntaSeleccionUnicaService unicaService;
    private final PreguntaSeleccionMultipleService multipleService;

    public GestionController(
            PreguntaVerdaderoFalsoService vfService,
            PreguntaSeleccionUnicaService unicaService,
            PreguntaSeleccionMultipleService multipleService) {
        this.vfService = vfService;
        this.unicaService = unicaService;
        this.multipleService = multipleService;
    }

    // GET /gestion-preguntas
    // Página principal de gestión de preguntas
    @GetMapping("/gestion-preguntas")
    public String gestionPreguntas(Model model) {
        // Contar cuántas preguntas hay de cada tipo
        long totalVF = vfService.count();
        long totalUnica = unicaService.count();
        long totalMultiple = multipleService.count();
        long total = totalVF + totalUnica + totalMultiple;

        // Añadir al modelo
        model.addAttribute("totalVF", totalVF);
        model.addAttribute("totalUnica", totalUnica);
        model.addAttribute("totalMultiple", totalMultiple);
        model.addAttribute("total", total);

        // Devolver vista: templates/gestion-preguntas.html
        return "gestion-preguntas";
    }
}