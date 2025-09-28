/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.vista;

import PackProyecto.controlador.AppController;
import PackProyecto.modelo.Alumno;
import PackProyecto.modelo.Persona;
import javax.swing.*;

/**
 *
 * @author mac
 */
public class VentanaUsuarios extends JDialog {
    private AppController controlador;
    private DefaultListModel<String> modeloLista;

    public VentanaUsuarios(JFrame owner, AppController controlador) {
        super(owner, "Administrar Usuarios", true);
        this.controlador = controlador;
        setSize(400, 350);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(null);

        modeloLista = new DefaultListModel<>();
        JList<String> listaUsuariosUI = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaUsuariosUI);
        scrollPane.setBounds(10, 10, 200, 290);
        panel.add(scrollPane);

        JButton btnAddAlumno = new JButton("Agregar Alumno");
        btnAddAlumno.setBounds(220, 10, 150, 25);
        panel.add(btnAddAlumno);

        JButton btnAddProfe = new JButton("Agregar Profesor");
        btnAddProfe.setBounds(220, 45, 150, 25);
        panel.add(btnAddProfe);

        add(panel);
        actualizarLista();

        btnAddAlumno.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "rut del alumno:");
            if (rut == null || rut.trim().isEmpty()) return;
            String nombre = JOptionPane.showInputDialog(this, "nombre del alumno:");
            if (nombre == null || nombre.trim().isEmpty()) return;
            String curso = JOptionPane.showInputDialog(this, "curso del alumno:");
            if (curso == null || curso.trim().isEmpty()) return;
            
            controlador.agregarNuevoAlumno(rut, nombre, curso);
            actualizarLista();
        });
        
        btnAddProfe.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "rut del profesor:");
            if (rut == null || rut.trim().isEmpty()) return;
            String nombre = JOptionPane.showInputDialog(this, "nombre del profesor:");
            if (nombre == null || nombre.trim().isEmpty()) return;
            String esp = JOptionPane.showInputDialog(this, "especialidad del profe:");
            if (esp == null || esp.trim().isEmpty()) return;
            
            controlador.agregarNuevoProfesor(rut, nombre, esp);
            actualizarLista();
        });
    }

    private void actualizarLista() {
        modeloLista.clear();
        for (Persona p : controlador.getUsuarios()) {
            String tipo = (p instanceof Alumno) ? "Alumno" : "Profesor";
            modeloLista.addElement(String.format("[%s] %s (%s)", tipo, p.getNombre(), p.getRut()));
        }
    }
}