/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

/**
 *
 * @author mac
 */
public class Nota {
    private Alumno alumno;
    private double nota;

    public Nota(Alumno alumno, double nota) {
        this.alumno = alumno;
        this.nota = nota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return alumno.getNombre() + " obtuvo " + nota;
    }
}