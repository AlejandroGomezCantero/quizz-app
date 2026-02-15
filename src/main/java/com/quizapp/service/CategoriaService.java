package com.quizapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.quizapp.model.entity.Categoria;
import com.quizapp.repository.CategoriaRepository;

// Service = Lógica de negocio (las reglas de tu aplicación)
// Aquí pones el "cerebro" de cómo funcionan las cosas

@Service
public class CategoriaService {

    // Necesitamos el Repository para hablar con la BD
    private final CategoriaRepository repo;

    // Constructor: Spring nos pasa el repository automáticamente
    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    // Método 1: Obtener TODAS las categorías
    // Ejemplo: findAll() devuelve [Historia, Ciencia, Deportes]
    public List<Categoria> findAll() {
        return repo.findAll();
    }

    // Método 2: Guardar una categoría (nueva o actualizada)
    // Si no tiene ID, la crea
    // Si tiene ID, la actualiza
    public Categoria save(Categoria categoria) {
        return repo.save(categoria);
    }

    // Método 3: Eliminar una categoría por su ID
    // Ejemplo: deleteById(3) elimina la categoría con ID=3
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}