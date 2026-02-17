package com.quizapp.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.service.CategoriaService;
import com.quizapp.service.ImportarService;

@Controller
@RequestMapping("/preguntas/importar")
public class ImportarController {

    private final ImportarService importarService;
    private final CategoriaService categoriaService;

    public ImportarController(ImportarService importarService, 
                            CategoriaService categoriaService) {
        this.importarService = importarService;
        this.categoriaService = categoriaService;
    }

    // GET /preguntas/importar
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "preguntas/importar";
    }

    // POST /preguntas/importar/procesar
    @PostMapping("/procesar")
    public String procesarArchivo(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam String tipoPreguntas,
            @RequestParam(required = false) Long categoriaId,
            RedirectAttributes redirectAttributes) {
        
        try {
            if (archivo.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", 
                    "Por favor selecciona un archivo");
                return "redirect:/preguntas/importar";
            }

            int importadas = importarService.importarDesdeCSV(
                archivo, tipoPreguntas, categoriaId);
            
            redirectAttributes.addFlashAttribute("exito", 
                "✅ Se importaron " + importadas + " preguntas correctamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ Error al importar: " + e.getMessage());
        }
        
        return "redirect:/preguntas/importar";
    }
}
