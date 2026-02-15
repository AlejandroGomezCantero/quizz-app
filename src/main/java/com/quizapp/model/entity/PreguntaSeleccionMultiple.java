package com.quizapp.model.entity;

import jakarta.persistence.*;
import java.util.List;

// Pregunta con múltiples opciones y varias respuestas correctas
// Ejemplo: ¿Cuáles son frutas? A) Manzana ✅ B) Tomate ✅ C) Lechuga ❌
@Entity
public class PreguntaSeleccionMultiple extends Pregunta {

    // Las 4 opciones de respuesta
    private String opcionA;
    private String opcionB;
    private String opcionC;
    private String opcionD;

    // Lista de respuestas correctas
    // Ejemplo: ["A", "B"] significa que A y B son correctas
    @ElementCollection
    private List<String> respuestasCorrectas;

    // ========== GETTERS Y SETTERS ==========
    
    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public List<String> getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public void setRespuestasCorrectas(List<String> respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }
}