/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author mac
 */
public class SistemaEvaluaciones {
    private List<Asignatura> listaAsignaturas;
    private List<Persona> listaUsuarios;
    private List<Nota> listaDeTodasLasNotas;
    private GestorArchivos gestorArchivos;

    public SistemaEvaluaciones() {
        this.listaAsignaturas = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.listaDeTodasLasNotas = new ArrayList<>();
        this.gestorArchivos = new GestorArchivos();
    }
    
    // --- getters ---
    public List<Asignatura> getListaAsignaturas() { return listaAsignaturas; }
    public List<Persona> getListaUsuarios() { return listaUsuarios; }
    public List<Nota> getListaDeTodasLasNotas() { return listaDeTodasLasNotas; }
    
    //gestion de usuarios basicos
    public Persona validarUsuario(String rut) {
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.add(new Alumno("juan perez", "1-1", "4to medio"));
            listaUsuarios.add(new Profesor("profesor", "2-2", "informatica"));
        }
        return listaUsuarios.stream().filter(u -> u.getRut().equals(rut)).findFirst().orElse(null);
    }
    
    public void agregarUsuario(Persona nuevoUsuario) throws ElementoDuplicadoException {
        boolean existe = listaUsuarios.stream().anyMatch(u -> u.getRut().equalsIgnoreCase(nuevoUsuario.getRut()));
        if (existe) {
            throw new ElementoDuplicadoException("el rut '" + nuevoUsuario.getRut() + "' ya esta registrado.");
        }
        listaUsuarios.add(nuevoUsuario);
    }

    // gestion de asignaturas 
    public void agregarAsignatura(Asignatura asignatura) throws ElementoDuplicadoException {
        boolean existe = listaAsignaturas.stream().anyMatch(a -> a.getCodigo().equalsIgnoreCase(asignatura.getCodigo()));
        if (existe) {
            throw new ElementoDuplicadoException("la asignatura con codigo " + asignatura.getCodigo() + " ya existe.");
        }
        listaAsignaturas.add(asignatura);
    }
    
    public void modificarAsignatura(String codigoActual, String nuevoCodigo, String nuevoNombre) throws ElementoNoEncontradoException, ElementoDuplicadoException {
        if (!codigoActual.equalsIgnoreCase(nuevoCodigo)) {
            boolean existe = listaAsignaturas.stream().anyMatch(a -> a.getCodigo().equalsIgnoreCase(nuevoCodigo));
            if (existe) {
                throw new ElementoDuplicadoException("el nuevo codigo '" + nuevoCodigo + "' ya esta en uso.");
            }
        }
        Asignatura aModificar = buscarAsignatura(codigoActual);
        aModificar.setCodigo(nuevoCodigo);
        aModificar.setNombre(nuevoNombre);
    }

    public Asignatura buscarAsignatura(String codigo) throws ElementoNoEncontradoException {
        return listaAsignaturas.stream().filter(a -> a.getCodigo().equalsIgnoreCase(codigo)).findFirst()
            .orElseThrow(() -> new ElementoNoEncontradoException("no se encontro la asignatura con codigo " + codigo));
    }

    public void eliminarAsignatura(String codigo) throws ElementoNoEncontradoException {
        Asignatura aEliminar = buscarAsignatura(codigo);
        listaAsignaturas.remove(aEliminar);
    }
    
    //logica de pruebas
    public Evaluacion crearPrueba(Asignatura asignatura, Alumno alumno, int numPreguntas) {
        if (asignatura == null || alumno == null) return null;
        ArrayList<Pregunta> todasLasPreguntas = new ArrayList<>();
        asignatura.getListaBancosDePreguntas().forEach(banco -> todasLasPreguntas.addAll(banco.getListaPreguntas()));
        if (todasLasPreguntas.isEmpty() || numPreguntas <= 0) return null;
        // revuleve
        java.util.Collections.shuffle(todasLasPreguntas);
        Evaluacion nuevaPrueba = new Evaluacion("Prueba de " + asignatura.getNombre(), (Profesor) validarUsuario("2-2"));
        nuevaPrueba.inscribirAlumno(alumno);
        int preguntasParaAgregar = Math.min(numPreguntas, todasLasPreguntas.size());
        todasLasPreguntas.stream().limit(preguntasParaAgregar).forEach(nuevaPrueba::agregarPregunta);
        return nuevaPrueba;
    }

    public Nota corregirPrueba(Evaluacion prueba, List<String> respuestasUsuario) {
        if (prueba == null || respuestasUsuario == null) return null;
        int puntajeTotal = prueba.getPreguntas().stream().mapToInt(Pregunta::getPuntaje).sum();
        int puntajeObtenido = 0;
        for (int i = 0; i < prueba.getPreguntas().size(); i++) {
            Pregunta p = prueba.getPreguntas().get(i);
            if (i < respuestasUsuario.size() && p.getRespuestaCorrecta().equalsIgnoreCase(respuestasUsuario.get(i))) {
                puntajeObtenido += p.getPuntaje();
            }
        }
        double notaFinal = (puntajeTotal > 0) ? 1.0 + 6.0 * ((double) puntajeObtenido / puntajeTotal) : 1.0;
        notaFinal = Math.round(notaFinal * 10.0) / 10.0;
        Nota nota = new Nota(prueba.getAlumnos().get(0), notaFinal, asignaturaDesdeNombrePrueba(prueba.getTema()));
        this.listaDeTodasLasNotas.add(nota);
        return nota;
    }

    private String asignaturaDesdeNombrePrueba(String nombrePrueba) {
        String nombreAsignatura = nombrePrueba.replace("Prueba de ", "");
        return listaAsignaturas.stream().filter(a -> a.getNombre().equals(nombreAsignatura)).findFirst()
            .map(Asignatura::getNombre).orElse("desconocida");
    }

    //reportes y exportacion
    public String generarReportePromedios() {
        if (listaDeTodasLasNotas.isEmpty()) return "no hay notas registradas.";
        Map<String, Double> promedios = listaDeTodasLasNotas.stream()
            .collect(Collectors.groupingBy(Nota::getCodigoAsignatura, Collectors.averagingDouble(Nota::getNota)));
        StringBuilder reporte = new StringBuilder("--- promedios por asignatura ---\n");
        promedios.forEach((asignatura, promedio) -> 
            reporte.append(String.format("- %s: promedio %.1f\n", asignatura, promedio)));
        return reporte.toString();
    }

    public void generarReporteAsignaturas(String nombreArchivo) throws IOException {
        gestorArchivos.generarReporteAsignaturas(this.listaAsignaturas, nombreArchivo);
    }
    
    public void exportarNotasCSV(String rutaArchivo) throws IOException {
        gestorArchivos.exportarNotas(listaDeTodasLasNotas, rutaArchivo);
    }
    
    // carga y guardado de datos
    public void cargarDatos() {
        this.listaUsuarios = gestorArchivos.cargarUsuarios();
        this.listaAsignaturas = gestorArchivos.cargarAsignaturas();
        gestorArchivos.cargarPreguntas(this.listaAsignaturas);
        this.listaDeTodasLasNotas = gestorArchivos.cargarNotas(this.listaUsuarios);
    }
    
    public void guardarDatos() {
        gestorArchivos.guardarUsuarios(listaUsuarios);
        gestorArchivos.guardarAsignaturas(listaAsignaturas);
        gestorArchivos.guardarPreguntas(listaAsignaturas);
        gestorArchivos.guardarNotas(listaDeTodasLasNotas);
    }
}