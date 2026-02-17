package com.quizapp.model.entity;

import jakarta.persistence.Entity;

@Entity
public class PreguntaVerdaderoFalso extends Pregunta {

    // Usar boolean (minúscula) es más seguro para validaciones lógicas
    private boolean respuestaCorrecta;

    // Cambia el nombre a 'is...' para que el Service lo encuentre
    public boolean isRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(boolean respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}
