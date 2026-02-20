package com.quizapp.controller.api;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quizapp.model.entity.PreguntaVerdaderoFalso;
import com.quizapp.service.PreguntaVerdaderoFalsoService;

@RestController
@RequestMapping("/api/vf")
public class PreguntaVerdaderoFalsoController {

    private final PreguntaVerdaderoFalsoService service;

    public PreguntaVerdaderoFalsoController(PreguntaVerdaderoFalsoService service) {
        this.service = service;
    }

    // ========== CRUD ==========

    @GetMapping
    public List<PreguntaVerdaderoFalso> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaVerdaderoFalso> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PreguntaVerdaderoFalso> crear(@RequestBody PreguntaVerdaderoFalso pregunta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pregunta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaVerdaderoFalso> actualizar(
            @PathVariable Long id,
            @RequestBody PreguntaVerdaderoFalso pregunta) {

        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        pregunta.setId(id);
        return ResponseEntity.ok(service.save(pregunta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ========== PAGINACIÓN ==========

    @GetMapping("/paginated")
    public Page<PreguntaVerdaderoFalso> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAllPaginated(PageRequest.of(page, size));
    }

    // ========== RANDOM — usado por el React ==========

    // GET /api/vf/random?cantidad=5
    // GET /api/vf/random?cantidad=5&categoriaId=2
    @GetMapping("/random")
    public ResponseEntity<?> getRandom(
            @RequestParam(defaultValue = "5") int cantidad,
            @RequestParam(required = false) Long categoriaId) {

        List<PreguntaVerdaderoFalso> todas;

        if (categoriaId != null) {
            Pageable pageable = PageRequest.of(0, 1000);
            todas = service.findByCategoriaId(categoriaId, pageable).getContent();
        } else {
            todas = service.findAll();
        }

        if (todas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay preguntas de Verdadero/Falso disponibles");
        }

        Collections.shuffle(todas);
        List<PreguntaVerdaderoFalso> resultado = todas.stream()
                .limit(cantidad)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    // ========== INFO ==========

    @GetMapping("/count")
    public long contar() {
        return service.count();
    }
}