package com.quizapp.model.entity;

import jakarta.persistence.Entity;

@Entity
public class PreguntaVerdaderoFalso extends Pregunta {

    private Boolean respuestaCorrecta;

    public Boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(Boolean respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}
