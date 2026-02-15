package com.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quizapp.model.entity.Categoria;

// Repository = Se encarga de hablar con la base de datos
// JpaRepository te da GRATIS estos métodos:
// - save() = guardar
// - findAll() = buscar todas
// - findById() = buscar por ID
// - deleteById() = eliminar por ID
// ¡No necesitas escribir código SQL!

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Aquí no necesitas escribir nada
    // Spring hace la magia automáticamente
}