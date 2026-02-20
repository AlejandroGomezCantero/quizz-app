package com.quizapp.controller.api;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quizapp.model.entity.PreguntaSeleccionUnica;
import com.quizapp.service.PreguntaSeleccionUnicaService;

@RestController
@RequestMapping("/api/seleccion-unica")
public class PreguntaSeleccionUnicaController {

    private final PreguntaSeleccionUnicaService service;

    public PreguntaSeleccionUnicaController(PreguntaSeleccionUnicaService service) {
        this.service = service;
    }

    // ========== CRUD ==========

    @GetMapping
    public List<PreguntaSeleccionUnica> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionUnica> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PreguntaSeleccionUnica> crear(@RequestBody PreguntaSeleccionUnica pregunta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pregunta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionUnica> actualizar(
            @PathVariable Long id,
            @RequestBody PreguntaSeleccionUnica pregunta) {

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
    public Page<PreguntaSeleccionUnica> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAllPaginated(PageRequest.of(page, size));
    }

    @GetMapping("/categoria/{categoriaId}")
    public Page<PreguntaSeleccionUnica> listarPorCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findByCategoriaId(categoriaId, PageRequest.of(page, size));
    }

    @GetMapping("/buscar")
    public Page<PreguntaSeleccionUnica> buscar(
            @RequestParam String texto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.buscarPorTexto(texto, PageRequest.of(page, size));
    }

    // ========== RANDOM — usado por el React ==========

    // GET /api/seleccion-unica/random?cantidad=5
    // GET /api/seleccion-unica/random?cantidad=5&categoriaId=2
    @GetMapping("/random")
    public ResponseEntity<?> getRandom(
            @RequestParam(defaultValue = "5") int cantidad,
            @RequestParam(required = false) Long categoriaId) {

        List<PreguntaSeleccionUnica> todas;

        if (categoriaId != null) {
            Pageable pageable = PageRequest.of(0, 1000);
            todas = service.findByCategoriaId(categoriaId, pageable).getContent();
        } else {
            todas = service.findAll();
        }

        if (todas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay preguntas de selección única disponibles");
        }

        Collections.shuffle(todas);
        List<PreguntaSeleccionUnica> resultado = todas.stream()
                .limit(cantidad)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    // ========== INFO ==========

    @GetMapping("/count")
    public long contar() {
        return service.count();
    }

    @GetMapping("/count/categoria/{categoriaId}")
    public long contarPorCategoria(@PathVariable Long categoriaId) {
        return service.countByCategoriaId(categoriaId);
    }
}