package com.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quizapp.model.entity.PreguntaVerdaderoFalso;
import com.quizapp.repository.PreguntaVerdaderoFalsoRepository;

@Service
public class PreguntaVerdaderoFalsoService {

    private final PreguntaVerdaderoFalsoRepository repo;

    public PreguntaVerdaderoFalsoService(PreguntaVerdaderoFalsoRepository repo) {
        this.repo = repo;
    }

    public Page<PreguntaVerdaderoFalso> buscarConFiltros(
            Long categoriaId, String texto, Pageable pageable) {
        return repo.buscarConFiltros(categoriaId, texto, pageable);
    }

    public List<PreguntaVerdaderoFalso> findAll() {
        return repo.findAll();
    }

    public Optional<PreguntaVerdaderoFalso> findById(Long id) {
        return repo.findById(id);
    }

    public PreguntaVerdaderoFalso save(PreguntaVerdaderoFalso pregunta) {
        return repo.save(pregunta);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public Page<PreguntaVerdaderoFalso> findAllPaginated(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Necesario para el endpoint /random con filtro de categoría
    public Page<PreguntaVerdaderoFalso> findByCategoriaId(Long categoriaId, Pageable pageable) {
        return repo.findByCategoriaId(categoriaId, pageable);
    }

    public long count() {
        return repo.count();
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}