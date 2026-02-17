package com.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quizapp.model.entity.PreguntaSeleccionUnica;
import com.quizapp.repository.PreguntaSeleccionUnicaRepository;

// Service = Lógica de negocio para preguntas de Selección Única
@Service
public class PreguntaSeleccionUnicaService {

    private final PreguntaSeleccionUnicaRepository repo;

    // Constructor
    public PreguntaSeleccionUnicaService(PreguntaSeleccionUnicaRepository repo) {
        this.repo = repo;
    }

    // ========== CRUD BÁSICO ==========

    // 1. READ - Obtener todas las preguntas
    public List<PreguntaSeleccionUnica> findAll() {
        return repo.findAll();
    }

    // 2. READ - Buscar una pregunta por ID
    // Ejemplo: findById(10) busca la pregunta con ID=10
    public Optional<PreguntaSeleccionUnica> findById(Long id) {
        return repo.findById(id);
    }

    // 3. CREATE/UPDATE - Guardar pregunta
    // Sin ID → Crea nueva
    // Con ID → Actualiza existente
    public PreguntaSeleccionUnica save(PreguntaSeleccionUnica pregunta) {
        return repo.save(pregunta);
    }

    // 4. DELETE - Eliminar pregunta por ID
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // ========== PAGINACIÓN ==========

    // Buscar con paginación
    // Ejemplo: findAllPaginated(page=0, size=10) → preguntas 1-10
    public Page<PreguntaSeleccionUnica> findAllPaginated(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // ========== FILTROS ==========

    // Buscar preguntas de una categoría específica
    // Ejemplo: findByCategoriaId(2, pageable) → solo preguntas de categoría 2
    public Page<PreguntaSeleccionUnica> findByCategoriaId(Long categoriaId, Pageable pageable) {
        return repo.findByCategoriaId(categoriaId, pageable);
    }

    // Buscar preguntas que contengan un texto
    // Ejemplo: buscarPorTexto("Francia") → preguntas que tengan "Francia"
    public Page<PreguntaSeleccionUnica> buscarPorTexto(String texto, Pageable pageable) {
        return repo.buscarPorTexto(texto, pageable);
    }

    
    public Page<PreguntaSeleccionUnica> buscarConFiltros(
            Long categoriaId, String texto, Pageable pageable) {
        return repo.buscarConFiltros(categoriaId, texto, pageable);
    }
    // ========== UTILIDADES ==========

    // Contar total de preguntas
    public long count() {
        return repo.count();
    }

    // Contar preguntas de una categoría
    public long countByCategoriaId(Long categoriaId) {
        return repo.countByCategoriaId(categoriaId);
    }

    // Verificar si existe
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}