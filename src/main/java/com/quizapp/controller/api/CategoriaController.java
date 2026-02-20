package com.quizapp.controller.api;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.quizapp.model.entity.Categoria;
import com.quizapp.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias") 
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> listar() {
        return service.findAll();
    }


    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return service.save(categoria);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.deleteById(id);
    }
}
