package com.quizapp.model.entity;

import jakarta.persistence.*;
import java.util.List;

// Categoría para organizar las preguntas
// Ejemplos: "Historia", "Ciencia", "Deportes", "Geografía"
@Entity
public class Categoria {

    // ID único de cada categoría (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la categoría
    // Ejemplo: "Historia"
    private String nombre;

    // Descripción de la categoría
    // Ejemplo: "Preguntas sobre eventos históricos"
    private String descripcion;

    // Relación: UNA categoría puede tener MUCHAS preguntas
    // Ejemplo: Categoría "Ciencia" tiene 20 preguntas
    @OneToMany(mappedBy = "categoria")
    private List<Pregunta> preguntas;

    // ========== GETTERS Y SETTERS ==========
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}