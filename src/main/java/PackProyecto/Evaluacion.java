/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

import java.util.ArrayList;

/**
 *
 * @author mac
 */
public class Evaluacion {
    private String tema;                       // tema o titulo de la evaluacion
    private ArrayList<Pregunta> preguntas;     // preguntas incluidas
    private Profesor profesor;                 // profe que la creo
    private ArrayList<Alumno> alumnos;         // alumnos que rindieron la evaluacion
    private ArrayList<Nota> notas;             // notas obtenidas

    // constructor
    public Evaluacion(String tema, Profesor profesor) {
        this.tema = tema;
        this.profesor = profesor;
        this.preguntas = new ArrayList<>();
        this.alumnos = new ArrayList<>();
        this.notas = new ArrayList<>();
    }

    // getters y setters
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    // metodos de gestion
    public boolean agregarPregunta(Pregunta p) {
        if (p == null) return false;
        if (buscarPregunta(p.getEnunciado()) != null) return false;
        return this.preguntas.add(p);
    }

    public boolean inscribirAlumno(Alumno a) {
        if (a == null) return false;
        if (buscarAlumno(a.getRut()) != null) return false;
        return this.alumnos.add(a);
    }

    public boolean registrarNota(Nota n) {
        if (n == null) return false;
        if (buscarNota(n.getAlumno().getRut()) != null) return false;
        return this.notas.add(n);
    }

    public void mostrarInfo() {
        System.out.println("evaluacion: " + tema);
        System.out.println("profesor: " + profesor.getNombre());
        System.out.println("preguntas: " + preguntas.size());
        System.out.println("alumnos inscritos: " + alumnos.size());
        System.out.println("notas registradas: " + notas.size());
    }

    // ALUMNOS
    // --------------------
    public boolean eliminarAlumno(String rut) {
        if (alumnos.isEmpty() || rut == null) return false;
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno a = alumnos.get(i);
            if (a.getRut().equals(rut)) {
                alumnos.remove(i);
                eliminarNota(rut); // tambien eliminamos su nota
                return true;
            }
        }
        return false;
    }

    public boolean modificarAlumno(String rut, String nuevoNombre, String nuevoCurso) {
        if (alumnos.isEmpty() || rut == null) return false;
        for (Alumno a : alumnos) {
            if (a.getRut().equals(rut)) {
                a.setNombre(nuevoNombre);
                a.setCurso(nuevoCurso);
                return true;
            }
        }
        return false;
    }

    public Alumno buscarAlumno(String rut) {
        if (alumnos.isEmpty() || rut == null) return null;
        for (Alumno a : alumnos) {
            if (a.getRut().equals(rut)) {
                return a;
            }
        }
        return null;
    }

    // NOTAS
    // --------------------
    public boolean eliminarNota(String rutAlumno) {
        if (notas.isEmpty() || rutAlumno == null) return false;
        for (int i = 0; i < notas.size(); i++) {
            Nota n = notas.get(i);
            if (n.getAlumno().getRut().equals(rutAlumno)) {
                notas.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean modificarNota(String rutAlumno, double nuevaNota) {
        if (notas.isEmpty() || rutAlumno == null) return false;
        for (Nota n : notas) {
            if (n.getAlumno().getRut().equals(rutAlumno)) {
                n.setNota(nuevaNota);
                return true;
            }
        }
        return false;
    }

    public Nota buscarNota(String rutAlumno) {
        if (notas.isEmpty() || rutAlumno == null) return null;
        for (Nota n : notas) {
            if (n.getAlumno().getRut().equals(rutAlumno)) {
                return n;
            }
        }
        return null;
    }

    // PREGUNTAS
    // --------------------
    public boolean eliminarPregunta(String enunciado) {
        if (preguntas.isEmpty() || enunciado == null) return false;
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            if (p.getEnunciado().equals(enunciado)) {
                preguntas.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean modificarPregunta(String enunciado, String nuevaRespuesta, int nuevoPuntaje) {
        if (preguntas.isEmpty() || enunciado == null) return false;
        for (Pregunta p : preguntas) {
            if (p.getEnunciado().equals(enunciado)) {
                p.setRespuestaCorrecta(nuevaRespuesta);
                p.setPuntaje(nuevoPuntaje);
                return true;
            }
        }
        return false;
    }
    public Pregunta buscarPregunta(String enunciado) {
        if (preguntas.isEmpty() || enunciado == null) return null;
        for (Pregunta p : preguntas) {
            if (p.getEnunciado().equals(enunciado)) {
                return p;
            }
        }
        return null;
    }
    
    
    //SIA2.5 funcionalidad propia
    //filtrar alumnos con nota >=40
    public ArrayList<Alumno> filtrarAlumnosAprobados() {
        ArrayList<Alumno> aprobados = new ArrayList<>();
        if (notas.isEmpty()) return aprobados;
        for (Nota n : notas) {
            if (n.getNota() >= 40) {
                aprobados.add(n.getAlumno());
            }
        }
        return aprobados;
    }
}