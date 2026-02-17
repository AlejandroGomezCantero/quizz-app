package com.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quizapp.model.entity.Test;
import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    
    // Este método es necesario para el historial que vimos en tu TestController
    List<Test> findByNombreUsuario(String nombreUsuario);
    
    // Opcional: buscar por estado (PENDIENTE/COMPLETADO)
    List<Test> findByEstado(String estado);
}