package com.quizapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quizapp.model.entity.PreguntaSeleccionUnica;

public interface PreguntaSeleccionUnicaRepository extends JpaRepository<PreguntaSeleccionUnica, Long> {

    Page<PreguntaSeleccionUnica> findByCategoriaId(Long categoriaId, Pageable pageable);

    long countByCategoriaId(Long categoriaId);

    @Query("SELECT p FROM PreguntaSeleccionUnica p WHERE LOWER(p.enunciado) LIKE LOWER(CONCAT('%', :texto, '%'))")
    Page<PreguntaSeleccionUnica> buscarPorTexto(@Param("texto") String texto, Pageable pageable);

    // NUEVO — filtro combinado
    @Query("SELECT p FROM PreguntaSeleccionUnica p WHERE " +
           "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
           "(:texto IS NULL OR LOWER(p.enunciado) LIKE LOWER(CONCAT('%', :texto, '%')))")
    Page<PreguntaSeleccionUnica> buscarConFiltros(
            @Param("categoriaId") Long categoriaId,
            @Param("texto") String texto,
            Pageable pageable);
}