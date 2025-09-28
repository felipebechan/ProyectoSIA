/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.modelo;

/**
 *
 * @author mac
 */
public class Nota {
    private Alumno alumno;
    private double nota;
    private String codigoAsignatura; // pa saber a que ramo pertenece

    public Nota(Alumno alumno, double nota, String codigoAsignatura) {
        this.alumno = alumno;
        this.nota = nota;
        this.codigoAsignatura = codigoAsignatura;
    }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    public String getCodigoAsignatura() { return codigoAsignatura; }
    public void setCodigoAsignatura(String codigoAsignatura) { this.codigoAsignatura = codigoAsignatura; }

    @Override
    public String toString() {
        return alumno.getNombre() + " obtuvo un " + nota + " en " + codigoAsignatura;
    }
}