/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mac
 */
public class SistemaEvaluaciones {
    private List<Asignatura> listaAsignaturas;
    private static final String ASIGNATURAS_FILE = "asignaturas.csv";
    private static final String PREGUNTAS_FILE = "preguntas.csv";

    public SistemaEvaluaciones() {
        this.listaAsignaturas = new ArrayList<>();
    }
    
    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    // gestion de asignaturas (SIA2.12)
    public void agregarAsignatura(Asignatura asignatura) throws ElementoDuplicadoException {
        for (Asignatura a : listaAsignaturas) {
            if (a.getCodigo().equalsIgnoreCase(asignatura.getCodigo())) {
                throw new ElementoDuplicadoException("la asignatura con codigo " + asignatura.getCodigo() + " ya existe.");
            }
        }
        listaAsignaturas.add(asignatura);
    }
    
    public Asignatura buscarAsignatura(String codigo) throws ElementoNoEncontradoException {
        for (Asignatura a : listaAsignaturas) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) {
                return a;
            }
        }
        throw new ElementoNoEncontradoException("no se encontro la asignatura con codigo " + codigo);
    }
    
    public void modificarAsignatura(String codigoActual, String nuevoCodigo, String nuevoNombre) throws ElementoNoEncontradoException, ElementoDuplicadoException {
        // revisar si el nuevo codigo ya existe en otra asignatura
        if (!codigoActual.equalsIgnoreCase(nuevoCodigo)) {
            for (Asignatura a : listaAsignaturas) {
                if (a.getCodigo().equalsIgnoreCase(nuevoCodigo)) {
                    throw new ElementoDuplicadoException("el nuevo codigo '" + nuevoCodigo + "' ya esta en uso.");
                }
            }
        }
        Asignatura aModificar = buscarAsignatura(codigoActual);
        aModificar.setCodigo(nuevoCodigo);
        aModificar.setNombre(nuevoNombre);
    }

    public void eliminarAsignatura(String codigo) throws ElementoNoEncontradoException {
        Asignatura aEliminar = buscarAsignatura(codigo);
        listaAsignaturas.remove(aEliminar);
    }

    // persistencia de datos (SIA2.2)
    public void cargarDatos() {
        cargarAsignaturasDesdeCSV();
        cargarPreguntasDesdeCSV();
    }
    
    private void cargarAsignaturasDesdeCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(ASIGNATURAS_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 2) {
                    Asignatura asignatura = new Asignatura(datos[0], datos[1]);
                    if(!listaAsignaturas.stream().anyMatch(a -> a.getCodigo().equals(asignatura.getCodigo()))){
                         listaAsignaturas.add(asignatura);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("ojo: no se pudo cargar " + ASIGNATURAS_FILE + ". se va a iniciar sin datos.");
        }
    }

    private void cargarPreguntasDesdeCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(PREGUNTAS_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 5) {
                    String codigoAsig = datos[0];
                    String temaBanco = datos[1];
                    String enunciado = datos[2];
                    String respuesta = datos[3];
                    int puntaje = Integer.parseInt(datos[4]);

                    Asignatura asignatura = buscarAsignatura(codigoAsig);
                    BancoDePreguntas banco;
                    try {
                        banco = asignatura.buscarBancoDePreguntas(temaBanco);
                    } catch (ElementoNoEncontradoException e) {
                        banco = new BancoDePreguntas(temaBanco);
                        asignatura.agregarBancoDePreguntas(banco);
                    }
                    
                    Pregunta pregunta = new Pregunta(enunciado, respuesta, puntaje);
                    banco.agregarPregunta(pregunta);
                }
            }
        } catch (IOException | ElementoNoEncontradoException | ElementoDuplicadoException e) {
            System.err.println("error al cargar las preguntas: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("error de formato en el puntaje de una pregunta en " + PREGUNTAS_FILE);
        }
    }

    public void guardarDatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ASIGNATURAS_FILE))) {
            for (Asignatura a : listaAsignaturas) {
                bw.write(a.getCodigo() + ";" + a.getNombre());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("error al guardar las asignaturas: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PREGUNTAS_FILE))) {
            for (Asignatura a : listaAsignaturas) {
                for (BancoDePreguntas b : a.getListaBancosDePreguntas()) {
                    for (Pregunta p : b.getListaPreguntas()) {
                        bw.write(a.getCodigo() + ";" + b.getTema() + ";" + p.getEnunciado() + ";" + p.getRespuestaCorrecta() + ";" + p.getPuntaje());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("error al guardar las preguntas: " + e.getMessage());
        }
    }

    // generacion de reporte (SIA2.10)
    public void generarReporteAsignaturas(String nombreArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write("--- REPORTE DEL SISTEMA ---\n\n");
            for (Asignatura a : listaAsignaturas) {
                bw.write("Asignatura: " + a.getNombre() + " (" + a.getCodigo() + ")\n");
                bw.write("------------------------------------------\n");
                if (a.getListaBancosDePreguntas().isEmpty()) {
                    bw.write("\tno tiene bancos de preguntas.\n");
                } else {
                    for (BancoDePreguntas b : a.getListaBancosDePreguntas()) {
                        bw.write("\t- banco: " + b.getTema() + " (" + b.getListaPreguntas().size() + " preguntas)\n");
                    }
                }
                bw.newLine();
            }
            System.out.println("reporte generado en el archivo " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("error al generar el reporte: " + e.getMessage());
        }
    }
}