/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.modelo;

/**
 *
 * @author mac
 */
public class Profesor extends Persona {
    private String especialidad;

    public Profesor(String nombre, String rut, String especialidad) {
        super(nombre, rut);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("profesor: " + nombre + " | rut: " + rut + " | especialidad: " + especialidad);
    }
}