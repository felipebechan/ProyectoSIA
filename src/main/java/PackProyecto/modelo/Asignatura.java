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
public class Asignatura {
    private String codigo;
    private String nombre;
    private ArrayList<BancoDePreguntas> listaBancosDePreguntas; // coleccion anidada

    public Asignatura(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.listaBancosDePreguntas = new ArrayList<>();
    }

    // getters y setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public ArrayList<BancoDePreguntas> getListaBancosDePreguntas() {
        return listaBancosDePreguntas;
    }

    public void agregarBancoDePreguntas(BancoDePreguntas banco) throws ElementoDuplicadoException {
        if (banco == null) throw new IllegalArgumentException("el banco de preguntas no puede ser nulo.");
        for (BancoDePreguntas existente : listaBancosDePreguntas) {
            if (existente.getTema().equalsIgnoreCase(banco.getTema())) {
                throw new ElementoDuplicadoException("ya existe un banco de preguntas con el tema: " + banco.getTema());
            }
        }
        this.listaBancosDePreguntas.add(banco);
    }

    public BancoDePreguntas buscarBancoDePreguntas(String tema) throws ElementoNoEncontradoException {
        for (BancoDePreguntas banco : listaBancosDePreguntas) {
            if (banco.getTema().equalsIgnoreCase(tema)) {
                return banco;
            }
        }
        throw new ElementoNoEncontradoException("no se encontro un banco de preguntas con el tema '" + tema + "' en la asignatura " + this.nombre);
    }

    public void eliminarBancoDePreguntas(String tema) throws ElementoNoEncontradoException {
        BancoDePreguntas banco = buscarBancoDePreguntas(tema);
        listaBancosDePreguntas.remove(banco);
    }

    public void modificarBancoDePreguntas(String tema, String nuevoTema) throws ElementoNoEncontradoException, ElementoDuplicadoException {
        for (BancoDePreguntas b : listaBancosDePreguntas) {
            if (b.getTema().equalsIgnoreCase(nuevoTema) && !tema.equalsIgnoreCase(nuevoTema)) {
                throw new ElementoDuplicadoException("ya existe un banco con el tema: " + nuevoTema);
            }
        }
        BancoDePreguntas banco = buscarBancoDePreguntas(tema);
        banco.setTema(nuevoTema);
    }

    public void mostrarBancosDePreguntas() {
        if (listaBancosDePreguntas.isEmpty()) {
            System.out.println("no hay bancos de preguntas registrados.");
        } else {
            System.out.println("bancos de preguntas de la asignatura " + nombre + ":");
            for (BancoDePreguntas banco : listaBancosDePreguntas) {
                System.out.println("- " + banco.getTema());
            }
        }
    }
}