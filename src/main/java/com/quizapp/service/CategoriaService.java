package com.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quizapp.model.entity.Categoria;
import com.quizapp.repository.CategoriaRepository;

// Service para gestionar Categorías
@Service
public class CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    // ========== MÉTODOS BÁSICOS ==========

    // Obtener todas las categorías
    public List<Categoria> findAll() {
        return repo.findAll();
    }

    // Buscar una categoría por ID
    public Optional<Categoria> findById(Long id) {
        return repo.findById(id);
    }

    // Buscar por nombre
    public Optional<Categoria> findByNombre(String nombre) {
        return repo.findByNombre(nombre);
    }

    // Guardar una categoría (crear o actualizar)
    public Categoria save(Categoria categoria) {
        return repo.save(categoria);
    }

    // Eliminar por ID
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // Verificar si existe
    public boolean existsByNombre(String nombre) {
        return repo.existsByNombre(nombre);
    }

    // ========== PAGINACIÓN ==========

    // Obtener categorías con paginación
    // Ejemplo: findAllPaginated(page=0, size=10) → primeras 10 categorías
    public Page<Categoria> findAllPaginated(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // ========== MÉTODO ÚTIL ==========

    // Obtener o crear una categoría
    // Si existe, la devuelve; si no, la crea
    public Categoria getOrCreate(String nombre) {
        return repo.findByNombre(nombre)
                .orElseGet(() -> {
                    Categoria nuevaCategoria = new Categoria();
                    nuevaCategoria.setNombre(nombre);
                    return repo.save(nuevaCategoria);
                });
    }
}