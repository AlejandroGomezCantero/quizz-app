package com.quizapp.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quizapp.model.entity.PreguntaSeleccionUnica;
import com.quizapp.service.PreguntaSeleccionUnicaService;

// Controller para preguntas de Selección Única
// URLs base: /api/seleccion-unica
@RestController
@RequestMapping("/api/seleccion-unica")
public class PreguntaSeleccionUnicaController {

    private final PreguntaSeleccionUnicaService service;

    public PreguntaSeleccionUnicaController(PreguntaSeleccionUnicaService service) {
        this.service = service;
    }

    // ========== CRUD COMPLETO ==========

    // 1. READ - Listar todas
    // GET http://localhost:8080/api/seleccion-unica
    @GetMapping
    public List<PreguntaSeleccionUnica> listar() {
        return service.findAll();
    }

    // 2. READ - Obtener una por ID
    // GET http://localhost:8080/api/seleccion-unica/15
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionUnica> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // 3. CREATE - Crear nueva pregunta
    // POST http://localhost:8080/api/seleccion-unica
    // Body (JSON):
    // {
    //   "enunciado": "¿Capital de España?",
    //   "opcionA": "Barcelona",
    //   "opcionB": "Madrid",
    //   "opcionC": "Valencia",
    //   "opcionCorrecta": "B",
    //   "categoria": { "id": 3 }
    // }
    @PostMapping
    public ResponseEntity<PreguntaSeleccionUnica> crear(@RequestBody PreguntaSeleccionUnica pregunta) {
        PreguntaSeleccionUnica nueva = service.save(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva); // 201 Created
    }

    // 4. UPDATE - Actualizar pregunta existente
    // PUT http://localhost:8080/api/seleccion-unica/15
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionUnica> actualizar(
            @PathVariable Long id,
            @RequestBody PreguntaSeleccionUnica pregunta) {
        
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        pregunta.setId(id);
        PreguntaSeleccionUnica actualizada = service.save(pregunta);
        return ResponseEntity.ok(actualizada);
    }

    // 5. DELETE - Eliminar pregunta
    // DELETE http://localhost:8080/api/seleccion-unica/15
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        service.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // ========== PAGINACIÓN ==========

    // Listar con paginación
    // GET http://localhost:8080/api/seleccion-unica/paginated?page=0&size=10
    @GetMapping("/paginated")
    public Page<PreguntaSeleccionUnica> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.findAllPaginated(pageable);
    }

    // ========== FILTROS ==========

    // Filtrar por categoría
    // GET http://localhost:8080/api/seleccion-unica/categoria/2?page=0&size=10
    @GetMapping("/categoria/{categoriaId}")
    public Page<PreguntaSeleccionUnica> listarPorCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.findByCategoriaId(categoriaId, pageable);
    }

    // Buscar por texto en el enunciado
    // GET http://localhost:8080/api/seleccion-unica/buscar?texto=capital&page=0&size=10
    @GetMapping("/buscar")
    public Page<PreguntaSeleccionUnica> buscar(
            @RequestParam String texto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.buscarPorTexto(texto, pageable);
    }

    // ========== INFORMACIÓN ==========

    // Contar todas las preguntas
    // GET http://localhost:8080/api/seleccion-unica/count
    @GetMapping("/count")
    public long contar() {
        return service.count();
    }

    // Contar preguntas de una categoría
    // GET http://localhost:8080/api/seleccion-unica/count/categoria/2
    @GetMapping("/count/categoria/{categoriaId}")
    public long contarPorCategoria(@PathVariable Long categoriaId) {
        return service.countByCategoriaId(categoriaId);
    }
}