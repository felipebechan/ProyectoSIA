/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.modelo;

import java.util.ArrayList;

/**
 *
 * @author mac
 */
public class BancoDePreguntas {
    private String tema;
    private ArrayList<Pregunta> listaPreguntas;

    public BancoDePreguntas(String tema) {
        this.tema = tema;
        this.listaPreguntas = new ArrayList<>();
    }

    // getters y setters
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    
    public ArrayList<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }
    
    public void agregarPregunta(Pregunta nuevaPregunta) throws ElementoDuplicadoException {
        if (nuevaPregunta == null) throw new IllegalArgumentException("la pregunta no puede ser nula.");
        for (Pregunta existente : listaPreguntas) {
            if (existente.getEnunciado().equalsIgnoreCase(nuevaPregunta.getEnunciado())) {
                throw new ElementoDuplicadoException("ya existe una pregunta con el enunciado: '" + nuevaPregunta.getEnunciado() + "'");
            }
        }
        listaPreguntas.add(nuevaPregunta);
    }

    public void agregarPregunta(String enunciado, String respuesta, int puntaje) throws ElementoDuplicadoException {
        if (enunciado == null || respuesta == null) throw new IllegalArgumentException("enunciado y respuesta no pueden ser nulos.");
        Pregunta nuevaPregunta = new Pregunta(enunciado, respuesta, puntaje);
        this.agregarPregunta(nuevaPregunta);
    }
    
    public void eliminarPregunta(String enunciado) throws ElementoNoEncontradoException {
        Pregunta pregunta = buscarPregunta(enunciado);
        listaPreguntas.remove(pregunta);
    }
    
    public void modificarPregunta(String enunciado, String nuevaRespuesta, int nuevoPuntaje) throws ElementoNoEncontradoException {
        Pregunta pregunta = buscarPregunta(enunciado);
        pregunta.setRespuestaCorrecta(nuevaRespuesta);
        pregunta.setPuntaje(nuevoPuntaje);
    }
    
    public Pregunta buscarPregunta(String enunciado) throws ElementoNoEncontradoException {
        if (enunciado == null) throw new IllegalArgumentException("el enunciado no puede ser nulo.");
        for (Pregunta p : listaPreguntas) {
            if (p.getEnunciado().equalsIgnoreCase(enunciado)) {
                return p;
            }
        }
        throw new ElementoNoEncontradoException("no se encontro la pregunta con el enunciado: '" + enunciado + "'");
    }
}