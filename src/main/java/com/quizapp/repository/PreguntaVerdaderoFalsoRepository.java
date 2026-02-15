package com.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quizapp.model.entity.PreguntaVerdaderoFalso;

public interface PreguntaVerdaderoFalsoRepository 
        extends JpaRepository<PreguntaVerdaderoFalso, Long> {
}
