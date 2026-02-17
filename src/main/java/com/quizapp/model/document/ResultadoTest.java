package com.quizapp.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "resultados_tests")
public class ResultadoTest {

    @Id
    private String id;

    private String username;
    private String tipoPreguntas; // VF, UNICA, MULTIPLE
    private int totalPreguntas;
    private int correctas;
    private int incorrectas;
    private double porcentaje;
    private LocalDateTime fecha;
    private String categoriaNombre;

    // Constructor vacío
    public ResultadoTest() {}

    // Constructor completo
    public ResultadoTest(String username, String tipoPreguntas,
                         int totalPreguntas, int correctas,
                         String categoriaNombre) {
        this.username = username;
        this.tipoPreguntas = tipoPreguntas;
        this.totalPreguntas = totalPreguntas;
        this.correctas = correctas;
        this.incorrectas = totalPreguntas - correctas;
        this.porcentaje = (correctas * 100.0) / totalPreguntas;
        this.categoriaNombre = categoriaNombre;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getTipoPreguntas() { return tipoPreguntas; }
    public void setTipoPreguntas(String tipoPreguntas) { this.tipoPreguntas = tipoPreguntas; }
    public int getTotalPreguntas() { return totalPreguntas; }
    public void setTotalPreguntas(int totalPreguntas) { this.totalPreguntas = totalPreguntas; }
    public int getCorrectas() { return correctas; }
    public void setCorrectas(int correctas) { this.correctas = correctas; }
    public int getIncorrectas() { return incorrectas; }
    public void setIncorrectas(int incorrectas) { this.incorrectas = incorrectas; }
    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
}