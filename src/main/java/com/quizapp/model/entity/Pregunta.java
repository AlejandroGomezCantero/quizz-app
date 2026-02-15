package com.quizapp.model.entity;

import jakarta.persistence.*;

// Esta es la clase PADRE de todas las preguntas
// Todas las preguntas (V/F, Selección Única, Múltiple) heredan de aquí
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pregunta {

    // ID único de cada pregunta (1, 2, 3, 4...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El texto de la pregunta
    // Ejemplo: "¿Cuál es la capital de Francia?"
    private String enunciado;

    // Relación: Muchas preguntas pueden pertenecer a UNA categoría
    // Ejemplo: 10 preguntas pueden ser de "Historia"
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // ========== GETTERS Y SETTERS ==========
    // Son métodos para leer y modificar los valores
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}