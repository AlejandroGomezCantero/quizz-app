package com.quizapp.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;
    private String tipoPreguntas; // VF, UNICA, MULTIPLE
    private int totalPreguntas;
    private int respuestasCorrectas;
    private double puntuacion;
    private String estado = "PENDIENTE"; // PENDIENTE, COMPLETADO

    @ManyToOne
    private Categoria categoria;

    @ElementCollection
    private List<Long> preguntasIds;

    @ElementCollection
    private List<String> respuestas;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Método que llama tu controlador
    public void calcularPuntuacion() {
        if (totalPreguntas > 0) {
            this.puntuacion = (double) respuestasCorrectas / totalPreguntas * 10;
        }
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getTipoPreguntas() { return tipoPreguntas; }
    public void setTipoPreguntas(String tipoPreguntas) { this.tipoPreguntas = tipoPreguntas; }
    public int getTotalPreguntas() { return totalPreguntas; }
    public void setTotalPreguntas(int totalPreguntas) { this.totalPreguntas = totalPreguntas; }
    public int getRespuestasCorrectas() { return respuestasCorrectas; }
    public void setRespuestasCorrectas(int respuestasCorrectas) { this.respuestasCorrectas = respuestasCorrectas; }
    public double getPuntuacion() { return puntuacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<Long> getPreguntasIds() { return preguntasIds; }
    public void setPreguntasIds(List<Long> preguntasIds) { this.preguntasIds = preguntasIds; }
    public List<String> getRespuestas() { return respuestas; }
    public void setRespuestas(List<String> respuestas) { this.respuestas = respuestas; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}