package com.quizapp.service;

import com.quizapp.model.document.ResultadoTest;
import com.quizapp.repository.ResultadoTestRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResultadoTestService {

    private final ResultadoTestRepository repo;

    public ResultadoTestService(ResultadoTestRepository repo) {
        this.repo = repo;
    }

    public ResultadoTest guardar(ResultadoTest resultado) {
        // Siempre calculamos en el backend — no confiamos en lo que manda el cliente
        resultado.setFecha(LocalDateTime.now());
        resultado.setIncorrectas(resultado.getTotalPreguntas() - resultado.getCorrectas());
        resultado.setPorcentaje((resultado.getCorrectas() * 100.0) / resultado.getTotalPreguntas());
        return repo.save(resultado);
    }

    public List<ResultadoTest> findByUsername(String username) {
        return repo.findByUsernameOrderByFechaDesc(username);
    }

    public List<ResultadoTest> findAll() {
        return repo.findAllByOrderByFechaDesc();
    }

    public long countByUsername(String username) {
        return repo.countByUsername(username);
    }
}