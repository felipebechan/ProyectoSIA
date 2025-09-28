/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mac
 */
public class VentanaPreguntas extends JDialog {
    private BancoDePreguntas banco;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPreguntasUI;

    public VentanaPreguntas(Dialog owner, BancoDePreguntas banco) {
        super(owner, "Preguntas del Banco: " + banco.getTema(), true);
        this.banco = banco;
        setSize(500, 400);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(null);

        modeloLista = new DefaultListModel<>();
        listaPreguntasUI = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaPreguntasUI);
        scrollPane.setBounds(10, 10, 300, 340);
        panel.add(scrollPane);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(320, 10, 150, 25);
        panel.add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(320, 45, 150, 25);
        panel.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(320, 80, 150, 25);
        panel.add(btnEliminar);

        add(panel);
        actualizarListaPreguntas();

        // EVENTOS
        btnAgregar.addActionListener(e -> {
            String enunciado = JOptionPane.showInputDialog(this, "ingresa el enunciado de la pregunta:");
            if (enunciado == null || enunciado.trim().isEmpty()) return;
            String respuesta = JOptionPane.showInputDialog(this, "ingresa la respuesta correcta:");
            if (respuesta == null || respuesta.trim().isEmpty()) return;
            String puntajeStr = JOptionPane.showInputDialog(this, "ingresa el puntaje:");
            
            try {
                int puntaje = Integer.parseInt(puntajeStr);
                banco.agregarPregunta(enunciado, respuesta, puntaje);
                actualizarListaPreguntas();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "el puntaje debe ser un numero.", "error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (ElementoDuplicadoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ups", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnModificar.addActionListener(e -> {
            int seleccionado = listaPreguntasUI.getSelectedIndex();
            if (seleccionado != -1) {
                try {
                    Pregunta preguntaActual = banco.getListaPreguntas().get(seleccionado);
                    String nuevaRespuesta = JOptionPane.showInputDialog(this, "ingresa la nueva respuesta:", preguntaActual.getRespuestaCorrecta());
                    if (nuevaRespuesta == null || nuevaRespuesta.trim().isEmpty()) return;
                    String nuevoPuntajeStr = JOptionPane.showInputDialog(this, "ingresa el nuevo puntaje:", preguntaActual.getPuntaje());

                    int nuevoPuntaje = Integer.parseInt(nuevoPuntajeStr);
                    banco.modificarPregunta(preguntaActual.getEnunciado(), nuevaRespuesta, nuevoPuntaje);
                    actualizarListaPreguntas(); 
                } catch (ElementoNoEncontradoException ex) {
                     JOptionPane.showMessageDialog(this, ex.getMessage(), "ups", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "el puntaje debe ser un numero.", "error de formato", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "selecciona una pregunta para modificar.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int seleccionado = listaPreguntasUI.getSelectedIndex();
            if (seleccionado != -1) {
                try {
                    Pregunta preguntaAEliminar = banco.getListaPreguntas().get(seleccionado);
                    int confirm = JOptionPane.showConfirmDialog(this, "seguro que quieres eliminar esta pregunta?", "confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        banco.eliminarPregunta(preguntaAEliminar.getEnunciado());
                        actualizarListaPreguntas();
                    }
                } catch (ElementoNoEncontradoException ex) {
                     JOptionPane.showMessageDialog(this, ex.getMessage(), "ups", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "selecciona una pregunta a eliminar.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void actualizarListaPreguntas() {
        modeloLista.clear();
        for (Pregunta p : banco.getListaPreguntas()) {
            modeloLista.addElement("p: " + p.getEnunciado() + " (valor: " + p.getPuntaje() + ")");
        }
    }
}