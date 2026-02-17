package com.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quizapp.model.entity.PreguntaVerdaderoFalso;
import com.quizapp.repository.PreguntaVerdaderoFalsoRepository;

// Service = Cerebro de la aplicación
// Aquí va toda la lógica de cómo funcionan las preguntas V/F
@Service
public class PreguntaVerdaderoFalsoService {

    private final PreguntaVerdaderoFalsoRepository repo;

    // Constructor: Spring nos da el repository automáticamente
    public PreguntaVerdaderoFalsoService(PreguntaVerdaderoFalsoRepository repo) {
        this.repo = repo;
    }
    
    
    public Page<PreguntaVerdaderoFalso> buscarConFiltros(
            Long categoriaId, String texto, Pageable pageable) {
        return repo.buscarConFiltros(categoriaId, texto, pageable);
    }

    // ========== CRUD BÁSICO ==========

    // 1. READ - Buscar TODAS las preguntas
    // Ejemplo: findAll() devuelve [pregunta1, pregunta2, pregunta3...]
    public List<PreguntaVerdaderoFalso> findAll() {
        return repo.findAll();
    }

    // 2. READ - Buscar UNA pregunta por su ID
    // Ejemplo: findById(5) busca la pregunta con ID=5
    // Devuelve Optional porque puede que no exista
    public Optional<PreguntaVerdaderoFalso> findById(Long id) {
        return repo.findById(id);
    }

    // 3. CREATE/UPDATE - Guardar una pregunta
    // Si no tiene ID → la CREA (nueva)
    // Si tiene ID → la ACTUALIZA (modifica la existente)
    public PreguntaVerdaderoFalso save(PreguntaVerdaderoFalso pregunta) {
        return repo.save(pregunta);
    }

    // 4. DELETE - Eliminar una pregunta por su ID
    // Ejemplo: deleteById(3) elimina la pregunta con ID=3
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // ========== PAGINACIÓN ==========

    // Buscar con paginación
    // Pageable tiene información como: página actual, tamaño, orden
    // Ejemplo: página 0, tamaño 10 → devuelve preguntas 1-10
    // Ejemplo: página 1, tamaño 10 → devuelve preguntas 11-20
    public Page<PreguntaVerdaderoFalso> findAllPaginated(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // ========== MÉTODOS ÚTILES ==========

    // Contar cuántas preguntas hay en total
    // Ejemplo: count() devuelve 25 si hay 25 preguntas
    public long count() {
        return repo.count();
    }

    // Verificar si existe una pregunta con ese ID
    // Ejemplo: existsById(10) devuelve true si existe, false si no
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}