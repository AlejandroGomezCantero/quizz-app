package com.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quizapp.model.entity.Categoria;
import java.util.Optional;

// Repository para Categoria
// JpaRepository ya te da métodos como: save, findAll, findById, deleteById
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Buscar una categoría por su nombre
    // Spring Data JPA crea el método automáticamente
    // Solo con el nombre del método, Spring sabe qué hacer
    Optional<Categoria> findByNombre(String nombre);
    
    // Verificar si existe una categoría con ese nombre
    // Devuelve true si existe, false si no
    boolean existsByNombre(String nombre);
}