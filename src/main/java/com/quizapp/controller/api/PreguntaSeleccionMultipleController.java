package com.quizapp.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;
import com.quizapp.service.PreguntaSeleccionMultipleService;

// Controller para preguntas de Selección Múltiple
// URLs base: /api/seleccion-multiple
@RestController
@RequestMapping("/api/seleccion-multiple")
public class PreguntaSeleccionMultipleController {

    private final PreguntaSeleccionMultipleService service;

    public PreguntaSeleccionMultipleController(PreguntaSeleccionMultipleService service) {
        this.service = service;
    }

    // ========== CRUD COMPLETO ==========

    // 1. READ - Listar todas
    // GET http://localhost:8080/api/seleccion-multiple
    @GetMapping
    public List<PreguntaSeleccionMultiple> listar() {
        return service.findAll();
    }

    // 2. READ - Obtener una por ID
    // GET http://localhost:8080/api/seleccion-multiple/20
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionMultiple> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE - Crear nueva
    // POST http://localhost:8080/api/seleccion-multiple
    // Body (JSON):
    // {
    //   "enunciado": "¿Cuáles son frutas?",
    //   "opcionA": "Manzana",
    //   "opcionB": "Tomate",
    //   "opcionC": "Lechuga",
    //   "opcionD": "Pera",
    //   "respuestasCorrectas": ["A", "B", "D"],
    //   "categoria": { "id": 2 }
    // }
    @PostMapping
    public ResponseEntity<PreguntaSeleccionMultiple> crear(@RequestBody PreguntaSeleccionMultiple pregunta) {
        PreguntaSeleccionMultiple nueva = service.save(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // 4. UPDATE - Actualizar existente
    // PUT http://localhost:8080/api/seleccion-multiple/20
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionMultiple> actualizar(
            @PathVariable Long id,
            @RequestBody PreguntaSeleccionMultiple pregunta) {
        
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        pregunta.setId(id);
        PreguntaSeleccionMultiple actualizada = service.save(pregunta);
        return ResponseEntity.ok(actualizada);
    }

    // 5. DELETE - Eliminar
    // DELETE http://localhost:8080/api/seleccion-multiple/20
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ========== PAGINACIÓN ==========

    // Listar con paginación
    // GET http://localhost:8080/api/seleccion-multiple/paginated?page=0&size=10
    @GetMapping("/paginated")
    public Page<PreguntaSeleccionMultiple> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.findAllPaginated(pageable);
    }

    // ========== FILTROS ==========

    // Filtrar por categoría
    // GET http://localhost:8080/api/seleccion-multiple/categoria/2?page=0&size=10
    @GetMapping("/categoria/{categoriaId}")
    public Page<PreguntaSeleccionMultiple> listarPorCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.findByCategoriaId(categoriaId, pageable);
    }

    // Buscar por texto
    // GET http://localhost:8080/api/seleccion-multiple/buscar?texto=frutas&page=0&size=10
    @GetMapping("/buscar")
    public Page<PreguntaSeleccionMultiple> buscar(
            @RequestParam String texto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.buscarPorTexto(texto, pageable);
    }

    // ========== INFORMACIÓN ==========

    // Contar total
    // GET http://localhost:8080/api/seleccion-multiple/count
    @GetMapping("/count")
    public long contar() {
        return service.count();
    }

    // Contar por categoría
    // GET http://localhost:8080/api/seleccion-multiple/count/categoria/2
    @GetMapping("/count/categoria/{categoriaId}")
    public long contarPorCategoria(@PathVariable Long categoriaId) {
        return service.countByCategoriaId(categoriaId);
    }
}