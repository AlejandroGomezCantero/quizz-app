package com.quizapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quizapp.model.entity.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class ImportarService {

    private final PreguntaVerdaderoFalsoService vfService;
    private final PreguntaSeleccionUnicaService unicaService;
    private final PreguntaSeleccionMultipleService multipleService;
    private final CategoriaService categoriaService;

    public ImportarService(
            PreguntaVerdaderoFalsoService vfService,
            PreguntaSeleccionUnicaService unicaService,
            PreguntaSeleccionMultipleService multipleService,
            CategoriaService categoriaService) {
        this.vfService = vfService;
        this.unicaService = unicaService;
        this.multipleService = multipleService;
        this.categoriaService = categoriaService;
    }

    public int importarDesdeCSV(MultipartFile archivo, String tipo, Long categoriaId) 
            throws Exception {
        
        int importadas = 0;
        Categoria categoria = null;
        
        if (categoriaId != null) {
            categoria = categoriaService.findById(categoriaId)
                .orElseThrow(() -> new Exception("Categoría no encontrada"));
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(archivo.getInputStream()))) {
            
            String linea;
            br.readLine(); // Saltar cabecera
            
            while ((linea = linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                
                switch (tipo) {
                    case "VF":
                        importadas += importarVF(datos, categoria);
                        break;
                    case "UNICA":
                        importadas += importarUnica(datos, categoria);
                        break;
                    case "MULTIPLE":
                        importadas += importarMultiple(datos, categoria);
                        break;
                }
            }
        }
        
        return importadas;
    }

    private int importarVF(String[] datos, Categoria categoria) {
        // Formato: enunciado;respuesta(true/false)
        if (datos.length < 2) return 0;
        
        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso();
        pregunta.setEnunciado(datos[0].trim());
        pregunta.setRespuestaCorrecta(Boolean.parseBoolean(datos[1].trim()));
        pregunta.setCategoria(categoria);
        
        vfService.save(pregunta);
        return 1;
    }

    private int importarUnica(String[] datos, Categoria categoria) {
        // Formato: enunciado;opcionA;opcionB;opcionC;respuestaCorrecta(A/B/C)
        if (datos.length < 5) return 0;
        
        PreguntaSeleccionUnica pregunta = new PreguntaSeleccionUnica();
        pregunta.setEnunciado(datos[0].trim());
        pregunta.setOpcionA(datos[1].trim());
        pregunta.setOpcionB(datos[2].trim());
        pregunta.setOpcionC(datos[3].trim());
        pregunta.setOpcionCorrecta(datos[4].trim().toUpperCase());
        pregunta.setCategoria(categoria);
        
        unicaService.save(pregunta);
        return 1;
    }

    private int importarMultiple(String[] datos, Categoria categoria) {
        // Formato: enunciado;A;B;C;D;correctas(A,B,C)
        if (datos.length < 6) return 0;
        
        PreguntaSeleccionMultiple pregunta = new PreguntaSeleccionMultiple();
        pregunta.setEnunciado(datos[0].trim());
        pregunta.setOpcionA(datos[1].trim());
        pregunta.setOpcionB(datos[2].trim());
        pregunta.setOpcionC(datos[3].trim());
        pregunta.setOpcionD(datos[4].trim());
        
        List<String> correctas = Arrays.asList(datos[5].trim().split(","));
        pregunta.setRespuestasCorrectas(correctas);
        pregunta.setCategoria(categoria);
        
        multipleService.save(pregunta);
        return 1;
    }
}
