/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

/**
 *
 * @author mac
 */
public class Alumno extends Persona {
    private String curso;

    public Alumno(String nombre, String rut, String curso) {
        super(nombre, rut);
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("alumno: " + nombre + " | rut: " + rut + " | curso: " + curso);
    }
}