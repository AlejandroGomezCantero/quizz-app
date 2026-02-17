package com.quizapp.service;

import com.quizapp.model.entity.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface TestService {
    // Métodos para el Test
    Test save(Test test);
    Optional<Test> findById(Long id);
    List<Test> findAll();
    List<Test> findByUsuario(String usuario);
    Map<String, Object> getEstadisticasUsuario(String usuario);

    // Generación de preguntas (basado en tu Switch)
    List<PreguntaVerdaderoFalso> generarTestVF(int cantidad, Long categoriaId);
    List<PreguntaSeleccionUnica> generarTestUnica(int cantidad, Long categoriaId);
    List<PreguntaSeleccionMultiple> generarTestMultiple(int cantidad, Long categoriaId);

    // Búsqueda de preguntas individuales
    Optional<PreguntaVerdaderoFalso> findPreguntaVFById(Long id);

    // Validación
    boolean validarRespuestaVF(Long preguntaId, boolean respuesta);
    boolean validarRespuestaUnica(Long preguntaId, String respuesta);
}