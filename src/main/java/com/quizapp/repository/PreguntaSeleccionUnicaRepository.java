package com.quizapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quizapp.model.entity.PreguntaSeleccionUnica;

// Repository = Habla con la base de datos
// JpaRepository te da GRATIS muchos métodos:
// - save(), findAll(), findById(), deleteById(), etc.
public interface PreguntaSeleccionUnicaRepository extends JpaRepository<PreguntaSeleccionUnica, Long> {
    
    // ========== MÉTODOS PERSONALIZADOS ==========
    
    // Buscar preguntas por categoría
    // Ejemplo: findByCategoriaId(2) devuelve todas las preguntas de la categoría con ID=2
    Page<PreguntaSeleccionUnica> findByCategoriaId(Long categoriaId, Pageable pageable);
    
    // Contar preguntas por categoría
    // Ejemplo: countByCategoriaId(2) devuelve cuántas preguntas hay de la categoría 2
    long countByCategoriaId(Long categoriaId);
    
    // Buscar preguntas que contengan un texto en el enunciado
    // Ejemplo: findByEnunciadoContaining("capital") busca preguntas que tengan la palabra "capital"
    @Query("SELECT p FROM PreguntaSeleccionUnica p WHERE LOWER(p.enunciado) LIKE LOWER(CONCAT('%', :texto, '%'))")
    Page<PreguntaSeleccionUnica> buscarPorTexto(@Param("texto") String texto, Pageable pageable);
}