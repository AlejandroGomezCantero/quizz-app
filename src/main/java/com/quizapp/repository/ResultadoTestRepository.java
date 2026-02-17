package com.quizapp.repository;

import com.quizapp.model.document.ResultadoTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ResultadoTestRepository extends MongoRepository<ResultadoTest, String> {

    // Historial de un usuario
    List<ResultadoTest> findByUsernameOrderByFechaDesc(String username);

    // Contar tests de un usuario
    long countByUsername(String username);

    // Todos ordenados por fecha
    List<ResultadoTest> findAllByOrderByFechaDesc();
}
