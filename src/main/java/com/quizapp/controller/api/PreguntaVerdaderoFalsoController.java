package com.quizapp.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quizapp.model.entity.PreguntaVerdaderoFalso;
import com.quizapp.service.PreguntaVerdaderoFalsoService;

// Controller = Crea las URLs de tu API
// Aquí defines qué pasa cuando alguien visita una URL
@RestController
@RequestMapping("/api/vf") // Todas las URLs empiezan con /api/vf
public class PreguntaVerdaderoFalsoController {

    private final PreguntaVerdaderoFalsoService service;

    // Constructor: Spring nos da el service automáticamente
    public PreguntaVerdaderoFalsoController(PreguntaVerdaderoFalsoService service) {
        this.service = service;
    }

    // ========== CRUD COMPLETO ==========

    // 1. READ - Listar TODAS las preguntas
    // GET http://localhost:8080/api/vf
    @GetMapping
    public List<PreguntaVerdaderoFalso> listar() {
        return service.findAll();
    }

    // 2. READ - Obtener UNA pregunta por ID
    // GET http://localhost:8080/api/vf/5 → busca la pregunta con ID=5
    // ResponseEntity permite devolver códigos de error como 404
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaVerdaderoFalso> obtenerPorId(@PathVariable Long id) {
        // Buscar la pregunta
        return service.findById(id)
                .map(pregunta -> ResponseEntity.ok(pregunta)) // Si existe → 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no existe → 404 Not Found
    }

    // 3. CREATE - Crear una NUEVA pregunta
    // POST http://localhost:8080/api/vf
    // El @RequestBody convierte el JSON que envías en un objeto Java
    @PostMapping
    public ResponseEntity<PreguntaVerdaderoFalso> crear(@RequestBody PreguntaVerdaderoFalso pregunta) {
        PreguntaVerdaderoFalso nuevaPregunta = service.save(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPregunta); // 201 Created
    }

    // 4. UPDATE - Actualizar una pregunta existente
    // PUT http://localhost:8080/api/vf/5 → actualiza la pregunta con ID=5
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaVerdaderoFalso> actualizar(
            @PathVariable Long id,
            @RequestBody PreguntaVerdaderoFalso pregunta) {
        
        // Verificar si existe
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        
        // Asegurar que el ID coincide
        pregunta.setId(id);
        
        // Guardar cambios
        PreguntaVerdaderoFalso actualizada = service.save(pregunta);
        return ResponseEntity.ok(actualizada); // 200 OK
    }

    // 5. DELETE - Eliminar una pregunta
    // DELETE http://localhost:8080/api/vf/5 → elimina la pregunta con ID=5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // Verificar si existe
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        
        // Eliminar
        service.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content (eliminado con éxito)
    }

    // ========== PAGINACIÓN ==========

    // Listar con paginación
    // GET http://localhost:8080/api/vf/paginated?page=0&size=10
    // page = número de página (empieza en 0)
    // size = cuántos elementos por página
    @GetMapping("/paginated")
    public Page<PreguntaVerdaderoFalso> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return service.findAllPaginated(pageable);
    }

    // ========== INFORMACIÓN ÚTIL ==========

    // Contar cuántas preguntas hay
    // GET http://localhost:8080/api/vf/count
    @GetMapping("/count")
    public long contar() {
        return service.count();
    }
}