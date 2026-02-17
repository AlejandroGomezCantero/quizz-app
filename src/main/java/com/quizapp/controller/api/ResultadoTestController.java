package com.quizapp.controller.api;

import com.quizapp.model.document.ResultadoTest;
import com.quizapp.service.ResultadoTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoTestController {

    private final ResultadoTestService service;

    public ResultadoTestController(ResultadoTestService service) {
        this.service = service;
    }

    // GET /api/resultados
    @GetMapping
    public List<ResultadoTest> listarTodos() {
        return service.findAll();
    }

    // GET /api/resultados/usuario/pepe
    @GetMapping("/usuario/{username}")
    public List<ResultadoTest> listarPorUsuario(@PathVariable String username) {
        return service.findByUsername(username);
    }

    // POST /api/resultados
    @PostMapping
    public ResponseEntity<ResultadoTest> guardar(@RequestBody ResultadoTest resultado) {
        return ResponseEntity.ok(service.guardar(resultado));
    }
}