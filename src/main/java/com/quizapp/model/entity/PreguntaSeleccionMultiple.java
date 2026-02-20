package com.quizapp.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class PreguntaSeleccionMultiple extends Pregunta {

    private String opcionA;
    private String opcionB;
    private String opcionC;
    private String opcionD;

    @ElementCollection
    private List<String> respuestasCorrectas;

    public String getOpcionA() { return opcionA; }
    public void setOpcionA(String opcionA) { this.opcionA = opcionA; }

    public String getOpcionB() { return opcionB; }
    public void setOpcionB(String opcionB) { this.opcionB = opcionB; }

    public String getOpcionC() { return opcionC; }
    public void setOpcionC(String opcionC) { this.opcionC = opcionC; }

    public String getOpcionD() { return opcionD; }
    public void setOpcionD(String opcionD) { this.opcionD = opcionD; }

    public List<String> getRespuestasCorrectas() { return respuestasCorrectas; }
    public void setRespuestasCorrectas(List<String> respuestasCorrectas) { this.respuestasCorrectas = respuestasCorrectas; }
}