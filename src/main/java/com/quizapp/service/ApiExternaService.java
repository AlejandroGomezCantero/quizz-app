package com.quizapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiExternaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PreguntaVerdaderoFalsoService vfService;
    private final PreguntaSeleccionMultipleService multipleService;
    private final CategoriaService categoriaService;

    public ApiExternaService(
            PreguntaVerdaderoFalsoService vfService,
            PreguntaSeleccionMultipleService multipleService,
            CategoriaService categoriaService) {
        this.vfService = vfService;
        this.multipleService = multipleService;
        this.categoriaService = categoriaService;
    }

    public boolean isApiDisponible() {
        try {
            String url = "https://opentdb.com/api.php?amount=1";
            restTemplate.getForObject(url, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int importarPreguntas(String tipo, int cantidad, String nombreCategoria) 
            throws Exception {

        // Tipo en la API: "boolean" para V/F, "multiple" para múltiple
        String tipoApi = tipo.equals("VF") ? "boolean" : "multiple";
        String url = "https://opentdb.com/api.php?amount=" + cantidad 
                   + "&type=" + tipoApi;

        String response = restTemplate.getForObject(url, String.class);
        JsonNode root = objectMapper.readTree(response);
        JsonNode results = root.get("results");

        if (results == null || results.isEmpty()) {
            throw new Exception("La API no devolvió preguntas");
        }

        // Obtener o crear la categoría
        Categoria categoria = categoriaService.getOrCreate(nombreCategoria);

        int importadas = 0;
        for (JsonNode item : results) {
            try {
                if (tipo.equals("VF")) {
                    importarVF(item, categoria);
                } else {
                    importarMultiple(item, categoria);
                }
                importadas++;
            } catch (Exception e) {
                // Si falla una, continuar con las demás
            }
        }

        return importadas;
    }

    private void importarVF(JsonNode item, Categoria categoria) {
        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso();
        pregunta.setEnunciado(limpiarTexto(item.get("question").asText()));
        pregunta.setRespuestaCorrecta(
            item.get("correct_answer").asText().equalsIgnoreCase("True")
        );
        pregunta.setCategoria(categoria);
        vfService.save(pregunta);
    }

    private void importarMultiple(JsonNode item, Categoria categoria) {
        PreguntaSeleccionMultiple pregunta = new PreguntaSeleccionMultiple();
        pregunta.setEnunciado(limpiarTexto(item.get("question").asText()));
        pregunta.setCategoria(categoria);

        String correcta = limpiarTexto(item.get("correct_answer").asText());
        JsonNode incorrectas = item.get("incorrect_answers");

        // Asignar opciones A, B, C, D
        pregunta.setOpcionA(correcta);
        if (incorrectas.size() > 0) 
            pregunta.setOpcionB(limpiarTexto(incorrectas.get(0).asText()));
        if (incorrectas.size() > 1) 
            pregunta.setOpcionC(limpiarTexto(incorrectas.get(1).asText()));
        if (incorrectas.size() > 2) 
            pregunta.setOpcionD(limpiarTexto(incorrectas.get(2).asText()));

        // La correcta siempre es A
        pregunta.setRespuestasCorrectas(java.util.List.of("A"));
        multipleService.save(pregunta);
    }

    // Limpiar caracteres HTML que devuelve la API
    private String limpiarTexto(String texto) {
        return texto
            .replace("&quot;", "\"")
            .replace("&#039;", "'")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&eacute;", "é")
            .replace("&oacute;", "ó");
    }
}