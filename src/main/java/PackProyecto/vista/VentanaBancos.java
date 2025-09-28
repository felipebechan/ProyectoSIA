/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.vista;

import PackProyecto.modelo.Asignatura;
import PackProyecto.modelo.BancoDePreguntas;
import PackProyecto.modelo.ElementoDuplicadoException;
import PackProyecto.modelo.ElementoNoEncontradoException;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mac
 */
public class VentanaBancos extends JDialog {
    private Asignatura asignatura;
    private JList<String> listaBancosUI;
    private DefaultListModel<String> modeloLista;

    public VentanaBancos(Frame owner, Asignatura asignatura) {
        super(owner, "Gestion de Bancos - " + asignatura.getNombre(), true);
        this.asignatura = asignatura;
        setSize(400, 350);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(null);

        modeloLista = new DefaultListModel<>();
        listaBancosUI = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaBancosUI);
        scrollPane.setBounds(10, 10, 200, 290);
        panel.add(scrollPane);

        JButton btnAgregar = new JButton("Agregar Banco");
        btnAgregar.setBounds(220, 10, 150, 25);
        panel.add(btnAgregar);

        JButton btnModificar = new JButton("Modificar Banco");
        btnModificar.setBounds(220, 45, 150, 25);
        panel.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar Banco");
        btnEliminar.setBounds(220, 80, 150, 25);
        panel.add(btnEliminar);
        
        JButton btnVerPreguntas = new JButton("Gestionar Preguntas");
        btnVerPreguntas.setBounds(220, 115, 150, 25);
        panel.add(btnVerPreguntas);

        add(panel);
        actualizarListaBancos();

        
        btnAgregar.addActionListener(e -> {
            String tema = JOptionPane.showInputDialog(this, "ingresa el tema del nuevo banco:");
            if (tema != null && !tema.trim().isEmpty()) {
                try {
                    asignatura.agregarBancoDePreguntas(new BancoDePreguntas(tema));
                    actualizarListaBancos();
                } catch (ElementoDuplicadoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "ups", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnModificar.addActionListener(e -> {
            int seleccionado = listaBancosUI.getSelectedIndex();
            if (seleccionado != -1) {
                String temaActual = asignatura.getListaBancosDePreguntas().get(seleccionado).getTema();
                String nuevoTema = JOptionPane.showInputDialog(this, "ingresa el nuevo tema para '" + temaActual + "':", temaActual);
                if (nuevoTema != null && !nuevoTema.trim().isEmpty()) {
                    try {
                        asignatura.modificarBancoDePreguntas(temaActual, nuevoTema);
                        actualizarListaBancos();
                    } catch (ElementoNoEncontradoException | ElementoDuplicadoException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "ups", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "selecciona un banco para modificar.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int seleccionado = listaBancosUI.getSelectedIndex();
            if (seleccionado != -1) {
                String tema = asignatura.getListaBancosDePreguntas().get(seleccionado).getTema();
                int confirm = JOptionPane.showConfirmDialog(this, "seguro que quieres eliminar el banco '" + tema + "'?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        asignatura.eliminarBancoDePreguntas(tema);
                        actualizarListaBancos();
                    } catch (ElementoNoEncontradoException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "ups", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "selecciona un banco para eliminar.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnVerPreguntas.addActionListener(e -> {
            int seleccionado = listaBancosUI.getSelectedIndex();
            if (seleccionado != -1) {
                BancoDePreguntas banco = asignatura.getListaBancosDePreguntas().get(seleccionado);
                new VentanaPreguntas(this, banco).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "selecciona un banco para ver sus preguntas.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void actualizarListaBancos() {
        modeloLista.clear();
        for (BancoDePreguntas banco : asignatura.getListaBancosDePreguntas()) {
            modeloLista.addElement(banco.getTema());
        }
    }
}