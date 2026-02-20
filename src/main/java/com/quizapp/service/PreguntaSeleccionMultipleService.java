package com.quizapp.service;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;
import com.quizapp.repository.PreguntaSeleccionMultipleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreguntaSeleccionMultipleService {

    private final PreguntaSeleccionMultipleRepository repo;

    public PreguntaSeleccionMultipleService(PreguntaSeleccionMultipleRepository repo) {
        this.repo = repo;
    }

    public List<PreguntaSeleccionMultiple> findAll() {
        return repo.findAll();
    }

    public Optional<PreguntaSeleccionMultiple> findById(Long id) {
        return repo.findById(id);
    }

    public PreguntaSeleccionMultiple save(PreguntaSeleccionMultiple pregunta) {
        return repo.save(pregunta);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public Page<PreguntaSeleccionMultiple> findAllPaginated(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<PreguntaSeleccionMultiple> findByCategoriaId(Long categoriaId, Pageable pageable) {
        return repo.findByCategoriaId(categoriaId, pageable);
    }

    public Page<PreguntaSeleccionMultiple> buscarPorTexto(String texto, Pageable pageable) {
        return repo.buscarPorTexto(texto, pageable);
    }

    public Page<PreguntaSeleccionMultiple> buscarConFiltros(Long categoriaId, String texto, Pageable pageable) {
        String textoFiltro = (texto != null && texto.trim().isEmpty()) ? null : texto;
        return repo.buscarConFiltros(categoriaId, textoFiltro, pageable);
    }

    public long count() {
        return repo.count();
    }

    public long countByCategoriaId(Long categoriaId) {
        return repo.countByCategoriaId(categoriaId);
    }
}
