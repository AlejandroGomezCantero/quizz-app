package com.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;
import com.quizapp.repository.PreguntaSeleccionMultipleRepository;

// Service para preguntas de Selección Múltiple
// (Preguntas con 4 opciones y varias respuestas correctas)
@Service
public class PreguntaSeleccionMultipleService {

    private final PreguntaSeleccionMultipleRepository repo;

    public PreguntaSeleccionMultipleService(PreguntaSeleccionMultipleRepository repo) {
        this.repo = repo;
    }

    // ========== CRUD BÁSICO ==========

    // 1. READ - Todas las preguntas
    public List<PreguntaSeleccionMultiple> findAll() {
        return repo.findAll();
    }

    // 2. READ - Una pregunta por ID
    public Optional<PreguntaSeleccionMultiple> findById(Long id) {
        return repo.findById(id);
    }

    // 3. CREATE/UPDATE - Guardar
    // Sin ID → crea nueva
    // Con ID → actualiza existente
    public PreguntaSeleccionMultiple save(PreguntaSeleccionMultiple pregunta) {
        return repo.save(pregunta);
    }

    // 4. DELETE - Eliminar
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // ========== PAGINACIÓN ==========

    // Listar con paginación
    public Page<PreguntaSeleccionMultiple> findAllPaginated(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // ========== FILTROS ==========

    // Filtrar por categoría
    public Page<PreguntaSeleccionMultiple> findByCategoriaId(Long categoriaId, Pageable pageable) {
        return repo.findByCategoriaId(categoriaId, pageable);
    }

    // Buscar por texto
    public Page<PreguntaSeleccionMultiple> buscarPorTexto(String texto, Pageable pageable) {
        return repo.buscarPorTexto(texto, pageable);
    }

    public Page<PreguntaSeleccionMultiple> buscarConFiltros(
            Long categoriaId, String texto, Pageable pageable) {
        return repo.buscarConFiltros(categoriaId, texto, pageable);
    }
    // ========== UTILIDADES ==========

    // Contar total
    public long count() {
        return repo.count();
    }

    // Contar por categoría
    public long countByCategoriaId(Long categoriaId) {
        return repo.countByCategoriaId(categoriaId);
    }

    // Verificar si existe
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}
