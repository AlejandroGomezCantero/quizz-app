package com.quizapp.controller.api;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;
import com.quizapp.service.PreguntaSeleccionMultipleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/seleccion-multiple")
public class PreguntaSeleccionMultipleController {

    private final PreguntaSeleccionMultipleService service;

    public PreguntaSeleccionMultipleController(PreguntaSeleccionMultipleService service) {
        this.service = service;
    }

    @GetMapping
    public List<PreguntaSeleccionMultiple> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionMultiple> obtenerPorId(@PathVariable Long id) {
        boolean existe = service.existsById(id);
        if (!existe) {
            return ResponseEntity.notFound().build();
        }
        PreguntaSeleccionMultiple pregunta = service.findById(id).get();
        return ResponseEntity.ok(pregunta);
    }

    @PostMapping
    public ResponseEntity<PreguntaSeleccionMultiple> crear(@RequestBody PreguntaSeleccionMultiple pregunta) {
        PreguntaSeleccionMultiple nueva = service.save(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaSeleccionMultiple> actualizar(@PathVariable Long id,
                                                                 @RequestBody PreguntaSeleccionMultiple pregunta) {
        boolean existe = service.existsById(id);
        if (!existe) {
            return ResponseEntity.notFound().build();
        }
        pregunta.setId(id);
        PreguntaSeleccionMultiple actualizada = service.save(pregunta);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean existe = service.existsById(id);
        if (!existe) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    public Page<PreguntaSeleccionMultiple> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAllPaginated(PageRequest.of(page, size));
    }

    @GetMapping("/count")
    public long contar() {
        return service.count();
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandom(
            @RequestParam(defaultValue = "5") int cantidad,
            @RequestParam(required = false) Long categoriaId) {

        List<PreguntaSeleccionMultiple> todas;

        if (categoriaId != null) {
            todas = service.findByCategoriaId(categoriaId, PageRequest.of(0, 1000)).getContent();
        } else {
            todas = service.findAll();
        }

        if (todas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay preguntas disponibles");
        }

        Collections.shuffle(todas);

        List<PreguntaSeleccionMultiple> resultado = todas.subList(0, Math.min(cantidad, todas.size()));

        return ResponseEntity.ok(resultado);
    }
}
