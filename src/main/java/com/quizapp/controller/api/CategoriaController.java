package com.quizapp.controller.api;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.quizapp.model.entity.Categoria;
import com.quizapp.service.CategoriaService;

// Controller = Crea las URLs de tu API
// @RestController = Devuelve datos en formato JSON
// Aquí defines qué pasa cuando alguien visita una URL

@RestController
@RequestMapping("/api/categorias") // Todas las URLs empiezan con /api/categorias
public class CategoriaController {

    // Necesitamos el Service para hacer las operaciones
    private final CategoriaService service;

    // Constructor: Spring nos pasa el service automáticamente
    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    // GET http://localhost:8080/api/categorias
    // Devuelve TODAS las categorías en formato JSON
    @GetMapping
    public List<Categoria> listar() {
        return service.findAll();
    }

    // POST http://localhost:8080/api/categorias
    // Crea una NUEVA categoría
    // El @RequestBody convierte el JSON que envías en un objeto Categoria
    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return service.save(categoria);
    }

    // DELETE http://localhost:8080/api/categorias/5
    // Elimina la categoría con ID = 5
    // El {id} en la URL se convierte en el parámetro "id"
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.deleteById(id);
    }
}
