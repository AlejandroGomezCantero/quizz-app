package com.quizapp.service;

import com.quizapp.model.document.ResultadoTest;
import com.quizapp.repository.ResultadoTestRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResultadoTestService {

    private final ResultadoTestRepository repo;

    public ResultadoTestService(ResultadoTestRepository repo) {
        this.repo = repo;
    }

    public ResultadoTest guardar(ResultadoTest resultado) {
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