package com.quizapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quizapp.model.entity.PreguntaSeleccionMultiple;

// Repository para preguntas de Selección Múltiple
public interface PreguntaSeleccionMultipleRepository extends JpaRepository<PreguntaSeleccionMultiple, Long> {
    
    // Buscar por categoría con paginación
    // Ejemplo: findByCategoriaId(3) → todas las preguntas de categoría 3
    Page<PreguntaSeleccionMultiple> findByCategoriaId(Long categoriaId, Pageable pageable);
    
    // Contar por categoría
    long countByCategoriaId(Long categoriaId);
    
    // Buscar por texto en el enunciado
    @Query("SELECT p FROM PreguntaSeleccionMultiple p WHERE LOWER(p.enunciado) LIKE LOWER(CONCAT('%', :texto, '%'))")
    Page<PreguntaSeleccionMultiple> buscarPorTexto(@Param("texto") String texto, Pageable pageable);
}
