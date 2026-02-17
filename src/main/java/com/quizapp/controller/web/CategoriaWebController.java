package com.quizapp.controller.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.model.entity.Categoria;
import com.quizapp.service.CategoriaService;

// @Controller (NO @RestController) = Devuelve páginas HTML
// Este controller gestiona las páginas web de categorías
@Controller
@RequestMapping("/categorias") // URLs base: /categorias
public class CategoriaWebController {

    private final CategoriaService service;

    // Constructor: Spring nos da el service automáticamente
    public CategoriaWebController(CategoriaService service) {
        this.service = service;
    }

    // ========== LISTAR CATEGORÍAS ==========
    
    // GET /categorias
    // Muestra la página con la lista de categorías
    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page, // Número de página (empieza en 0)
            Model model) { // Model = contenedor de datos para la vista
        
        // Configurar paginación: 10 categorías por página
        Pageable pageable = PageRequest.of(page, 10);
        
        // Obtener categorías de la base de datos
        Page<Categoria> paginaCategorias = service.findAllPaginated(pageable);
        
        // Añadir datos al Model para que la vista los use
        model.addAttribute("categorias", paginaCategorias.getContent()); // Lista de categorías
        model.addAttribute("paginaActual", page); // Página actual
        model.addAttribute("totalPaginas", paginaCategorias.getTotalPages()); // Total de páginas
        model.addAttribute("totalElementos", paginaCategorias.getTotalElements()); // Total de categorías
        
        // Devolver el nombre de la vista (archivo HTML)
        // Spring buscará: templates/categorias/listar.html
        return "categorias/listar";
    }

    // ========== MOSTRAR FORMULARIO PARA CREAR ==========
    
    // GET /categorias/nueva
    // Muestra el formulario vacío para crear una categoría
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        // Crear una categoría vacía
        Categoria categoria = new Categoria();
        
        // Añadirla al modelo
        model.addAttribute("categoria", categoria);
        model.addAttribute("titulo", "Nueva Categoría");
        model.addAttribute("esNueva", true); // Para saber si es crear o editar
        
        // Devolver la vista del formulario
        // Spring buscará: templates/categorias/formulario.html
        return "categorias/formulario";
    }

    // ========== MOSTRAR FORMULARIO PARA EDITAR ==========
    
    // GET /categorias/editar/5
    // Muestra el formulario con los datos de la categoría ID=5
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        // Buscar la categoría por ID
        Categoria categoria = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        // Añadir al modelo
        model.addAttribute("categoria", categoria);
        model.addAttribute("titulo", "Editar Categoría");
        model.addAttribute("esNueva", false); // Es edición, no creación
        
        // Devolver la misma vista del formulario
        return "categorias/formulario";
    }

    // ========== GUARDAR CATEGORÍA (CREAR O ACTUALIZAR) ==========
    
    // POST /categorias/guardar
    // Recibe los datos del formulario y los guarda
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Categoria categoria, // Los datos del formulario se convierten en objeto Categoria
            RedirectAttributes redirectAttributes) { // Para mostrar mensajes después de guardar
        
        try {
            // Guardar en la base de datos
            service.save(categoria);
            
            // Mensaje de éxito
            String mensaje = (categoria.getId() == null) 
                ? "Categoría creada con éxito" 
                : "Categoría actualizada con éxito";
            redirectAttributes.addFlashAttribute("mensajeExito", mensaje);
            
        } catch (Exception e) {
            // Si hay error, mostrar mensaje
            redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar: " + e.getMessage());
        }
        
        // Redirigir a la lista de categorías
        // redirect: = no es una vista, es una redirección a otra URL
        return "redirect:/categorias";
    }

    // ========== ELIMINAR CATEGORÍA ==========
    
    // GET /categorias/eliminar/5
    // Elimina la categoría con ID=5
    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Eliminar de la base de datos
            service.deleteById(id);
            
            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("mensajeExito", "Categoría eliminada con éxito");
            
        } catch (Exception e) {
            // Si hay error (por ejemplo, tiene preguntas asociadas)
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar: " + e.getMessage());
        }
        
        // Redirigir a la lista
        return "redirect:/categorias";
    }
}