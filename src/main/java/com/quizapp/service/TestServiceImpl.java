package com.quizapp.service;

import org.springframework.stereotype.Service;
import com.quizapp.model.entity.*;
import com.quizapp.repository.*;
import com.quizapp.service.TestService;

import java.util.*;

@Service // ESTA ANOTACIÓN ES LA QUE ARREGLA TU ERROR
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    // Asumo que tienes estos repositorios creados
    private final PreguntaVerdaderoFalsoRepository vfRepository;
    private final PreguntaSeleccionUnicaRepository unicaRepository;

    public TestServiceImpl(TestRepository testRepository, 
                           PreguntaVerdaderoFalsoRepository vfRepository,
                           PreguntaSeleccionUnicaRepository unicaRepository) {
        this.testRepository = testRepository;
        this.vfRepository = vfRepository;
        this.unicaRepository = unicaRepository;
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

    @Override
    public List<PreguntaVerdaderoFalso> generarTestVF(int cantidad, Long categoriaId) {
        // Lógica simple: traer todas y limitar (o usar un query nativo ORDER BY RAND())
        return vfRepository.findAll().stream().limit(cantidad).toList();
    }

    @Override
    public Optional<PreguntaVerdaderoFalso> findPreguntaVFById(Long id) {
        return vfRepository.findById(id);
    }

    @Override
    public boolean validarRespuestaVF(Long preguntaId, boolean respuestaUsuario) {
        return vfRepository.findById(preguntaId)
                .map(p -> p.isRespuestaCorrecta() == respuestaUsuario)
                .orElse(false);
    }

    // Implementa los demás métodos de la interfaz...
    @Override public List<Test> findAll() { return testRepository.findAll(); }
    @Override public List<Test> findByUsuario(String usuario) { return testRepository.findByNombreUsuario(usuario); }
    @Override public List<PreguntaSeleccionUnica> generarTestUnica(int cantidad, Long categoriaId) { return List.of(); }
    @Override public List<PreguntaSeleccionMultiple> generarTestMultiple(int cantidad, Long categoriaId) { return List.of(); }
    @Override public boolean validarRespuestaUnica(Long id, String resp) { return false; }
    @Override public Map<String, Object> getEstadisticasUsuario(String usuario) { return new HashMap<>(); }}
