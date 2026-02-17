package com.quizapp.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.model.entity.*;
import com.quizapp.service.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;
    private final CategoriaService categoriaService;

    public TestController(TestService testService, CategoriaService categoriaService) {
        this.testService = testService;
        this.categoriaService = categoriaService;
    }

    // GET /tests - Página principal de tests
    @GetMapping
    public String index(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "tests/index";
    }

    // GET /tests/nuevo - Configurar nuevo test
    @GetMapping("/nuevo")
    public String configurarTest(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "tests/configurar";
    }

    // POST /tests/generar - Generar test
    @PostMapping("/generar")
    public String generarTest(
            @RequestParam String tipo,
            @RequestParam int cantidad,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam String nombreUsuario,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Crear nuevo test
            Test test = new Test();
            test.setNombreUsuario(nombreUsuario);
            test.setTipoPreguntas(tipo);
            test.setTotalPreguntas(cantidad);
            
            if (categoriaId != null) {
                categoriaService.findById(categoriaId).ifPresent(test::setCategoria);
            }
            
            // Generar preguntas según tipo
            List<Long> preguntasIds = new ArrayList<>();
            
            switch (tipo) {
                case "VF":
                    testService.generarTestVF(cantidad, categoriaId)
                        .forEach(p -> preguntasIds.add(p.getId()));
                    break;
                case "UNICA":
                    testService.generarTestUnica(cantidad, categoriaId)
                        .forEach(p -> preguntasIds.add(p.getId()));
                    break;
                case "MULTIPLE":
                    testService.generarTestMultiple(cantidad, categoriaId)
                        .forEach(p -> preguntasIds.add(p.getId()));
                    break;
            }
            
            test.setPreguntasIds(preguntasIds);
            Test testGuardado = testService.save(test);
            
            return "redirect:/tests/realizar/" + testGuardado.getId();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al generar test");
            return "redirect:/tests/nuevo";
        }
    }

    // GET /tests/realizar/{id} - Realizar test
    @GetMapping("/realizar/{id}")
    public String realizarTest(@PathVariable Long id, Model model) {
        Test test = testService.findById(id)
            .orElseThrow(() -> new RuntimeException("Test no encontrado"));
        
        model.addAttribute("test", test);
        
        // Cargar preguntas según tipo
        List<?> preguntas = new ArrayList<>();
        switch (test.getTipoPreguntas()) {
            case "VF":
                preguntas = test.getPreguntasIds().stream()
                    .map(testService::findPreguntaVFById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(java.util.stream.Collectors.toList());
                break;
            // Similar para UNICA y MULTIPLE
        }
        
        model.addAttribute("preguntas", preguntas);
        return "tests/realizar";
    }

    // POST /tests/validar/{id} - Validar test
    @PostMapping("/validar/{id}")
    public String validarTest(
            @PathVariable Long id,
            @RequestParam Map<String, String> respuestas,
            RedirectAttributes redirectAttributes) {
        
        Test test = testService.findById(id)
            .orElseThrow(() -> new RuntimeException("Test no encontrado"));
        
        int correctas = 0;
        List<String> respuestasUsuario = new ArrayList<>();
        
        // Validar cada respuesta
        for (int i = 0; i < test.getTotalPreguntas(); i++) {
            Long preguntaId = test.getPreguntasIds().get(i);
            String respuesta = respuestas.get("pregunta_" + i);
            respuestasUsuario.add(respuesta);
            
            boolean esCorrecta = false;
            switch (test.getTipoPreguntas()) {
                case "VF":
                    esCorrecta = testService.validarRespuestaVF(
                        preguntaId, Boolean.parseBoolean(respuesta));
                    break;
                case "UNICA":
                    esCorrecta = testService.validarRespuestaUnica(preguntaId, respuesta);
                    break;
                case "MULTIPLE":
                    // Manejar múltiples respuestas
                    break;
            }
            
            if (esCorrecta) correctas++;
        }
        
        // Actualizar test
        test.setRespuestasCorrectas(correctas);
        test.calcularPuntuacion();
        test.setEstado("COMPLETADO");
        test.setRespuestas(respuestasUsuario);
        testService.save(test);
        
        return "redirect:/tests/resultado/" + id;
    }

    // GET /tests/resultado/{id} - Ver resultado
    @GetMapping("/resultado/{id}")
    public String verResultado(@PathVariable Long id, Model model) {
        Test test = testService.findById(id)
            .orElseThrow(() -> new RuntimeException("Test no encontrado"));
        
        model.addAttribute("test", test);
        return "tests/resultado";
    }

    // GET /tests/historial - Ver historial
    @GetMapping("/historial")
    public String historial(@RequestParam(required = false) String usuario, Model model) {
        if (usuario != null && !usuario.isEmpty()) {
            model.addAttribute("tests", testService.findByUsuario(usuario));
            model.addAttribute("stats", testService.getEstadisticasUsuario(usuario));
        } else {
            model.addAttribute("tests", testService.findAll());
        }
        return "tests/historial";
    }
}
