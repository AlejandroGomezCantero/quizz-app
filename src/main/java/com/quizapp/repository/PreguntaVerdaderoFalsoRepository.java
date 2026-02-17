package com.quizapp.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quizapp.model.entity.PreguntaVerdaderoFalso;

public interface PreguntaVerdaderoFalsoRepository 
extends JpaRepository<PreguntaVerdaderoFalso, Long> {

Page<PreguntaVerdaderoFalso> findByCategoriaId(Long categoriaId, Pageable pageable);

Page<PreguntaVerdaderoFalso> findByEnunciadoContainingIgnoreCase(String enunciado, Pageable pageable);

// NUEVO: filtro combinado categoría + texto
@Query("SELECT p FROM PreguntaVerdaderoFalso p WHERE " +
   "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
   "(:texto IS NULL OR LOWER(p.enunciado) LIKE LOWER(CONCAT('%', :texto, '%')))")
Page<PreguntaVerdaderoFalso> buscarConFiltros(
    @Param("categoriaId") Long categoriaId,
    @Param("texto") String texto,
    Pageable pageable);
}
