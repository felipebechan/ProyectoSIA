/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author mac
 */
public class GestorArchivos {
    private static final String ASIGNATURAS_FILE = "asignaturas.csv";
    private static final String PREGUNTAS_FILE = "preguntas.csv";
    private static final String USUARIOS_FILE = "usuarios.csv";
    private static final String NOTAS_FILE = "notas.csv";


    public List<Persona> cargarUsuarios() {
        List<Persona> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d[0].equals("Alumno")) usuarios.add(new Alumno(d[2], d[1], d[3]));
                else if (d[0].equals("Profesor")) usuarios.add(new Profesor(d[2], d[1], d[3]));
            }
        } catch (IOException e) {
            System.err.println("no se encontro " + USUARIOS_FILE + ", se empieza sin usuarios (o con los de prueba)");
        }
        return usuarios;
    }

    public List<Asignatura> cargarAsignaturas() {
        List<Asignatura> asignaturas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ASIGNATURAS_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 2) {
                    asignaturas.add(new Asignatura(datos[0], datos[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("no se pudo cargar " + ASIGNATURAS_FILE);
        }
        return asignaturas;
    }
    
    public void cargarPreguntas(List<Asignatura> asignaturas) {
        try (BufferedReader br = new BufferedReader(new FileReader(PREGUNTAS_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length == 5) {
                    Asignatura a = asignaturas.stream().filter(asig -> asig.getCodigo().equals(d[0])).findFirst().orElse(null);
                    if (a != null) {
                        BancoDePreguntas b;
                        try {
                            b = a.buscarBancoDePreguntas(d[1]);
                        } catch (ElementoNoEncontradoException e) {
                            b = new BancoDePreguntas(d[1]);
                            a.agregarBancoDePreguntas(b);
                        }
                        b.agregarPregunta(new Pregunta(d[2], d[3], Integer.parseInt(d[4])));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("error al cargar preguntas: " + e.getMessage());
        }
    }

    public List<Nota> cargarNotas(List<Persona> usuarios) {
        List<Nota> notas = new ArrayList<>();
        Map<String, Alumno> mapaAlumnos = usuarios.stream().filter(p -> p instanceof Alumno).map(p -> (Alumno) p).collect(Collectors.toMap(Persona::getRut, a -> a));
        try (BufferedReader br = new BufferedReader(new FileReader(NOTAS_FILE))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                Alumno al = mapaAlumnos.get(d[0]);
                if (al != null) notas.add(new Nota(al, Double.parseDouble(d[2]), d[1]));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("no se encontro " + NOTAS_FILE);
        }
        return notas;
    }


    public void guardarUsuarios(List<Persona> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USUARIOS_FILE))) {
            bw.write("Tipo;RUT;Nombre;Extra\n");
            for (Persona p : usuarios) {
                if (p instanceof Alumno) {
                    Alumno a = (Alumno) p;
                    bw.write(String.format("Alumno;%s;%s;%s\n", a.getRut(), a.getNombre(), a.getCurso()));
                } else if (p instanceof Profesor) {
                    Profesor pr = (Profesor) p;
                    bw.write(String.format("Profesor;%s;%s;%s\n", pr.getRut(), pr.getNombre(), pr.getEspecialidad()));
                }
            }
        } catch (IOException e) {
            System.err.println("error al guardar usuarios");
        }
    }
    
    public void guardarAsignaturas(List<Asignatura> asignaturas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ASIGNATURAS_FILE))) {
            for (Asignatura a : asignaturas) {
                bw.write(a.getCodigo() + ";" + a.getNombre() + "\n");
            }
        } catch (IOException e) {
            System.err.println("error al guardar asignaturas");
        }
    }

    public void guardarPreguntas(List<Asignatura> asignaturas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PREGUNTAS_FILE))) {
            for (Asignatura a : asignaturas) {
                for (BancoDePreguntas b : a.getListaBancosDePreguntas()) {
                    for (Pregunta p : b.getListaPreguntas()) {
                        bw.write(String.format("%s;%s;%s;%s;%d\n", a.getCodigo(), b.getTema(), p.getEnunciado(), p.getRespuestaCorrecta(), p.getPuntaje()));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("error al guardar preguntas");
        }
    }

    public void guardarNotas(List<Nota> notas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOTAS_FILE))) {
            bw.write("RUT Alumno;Asignatura;Nota\n");
            for (Nota n : notas) {
                bw.write(String.format("%s;%s;%.1f\n", n.getAlumno().getRut(), n.getCodigoAsignatura(), n.getNota()));
            }
        } catch (IOException e) {
            System.err.println("error al guardar notas");
        }
    }
    
    
    public void generarReporteAsignaturas(List<Asignatura> asignaturas, String nombreArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write("--- REPORTE DEL SISTEMA ---\n\n");
            for (Asignatura a : asignaturas) {
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
        }
    }
    
    public void exportarNotas(List<Nota> notas, String rutaArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("RUT Alumno;Nombre Alumno;Asignatura;Nota\n");
            for (Nota n : notas) {
                String linea = String.format("%s;%s;%s;%.1f\n",
                    n.getAlumno().getRut(),
                    n.getAlumno().getNombre(),
                    n.getCodigoAsignatura(),
                    n.getNota());
                bw.write(linea);
            }
        }
    }
}