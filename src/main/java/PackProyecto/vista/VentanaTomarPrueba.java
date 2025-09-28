/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.vista;

import PackProyecto.modelo.Pregunta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author mac
 */
public class VentanaTomarPrueba extends JDialog {
    private List<Pregunta> preguntas;
    private List<String> respuestas;
    private int preguntaActual = 0;
    
    private JLabel lblEnunciado;
    private JTextField txtRespuesta;
    private boolean terminada = false;

    public VentanaTomarPrueba(JFrame owner, List<Pregunta> preguntas) {
        super(owner, "Respondiendo la Prueba", true);
        this.preguntas = preguntas;
        this.respuestas = new ArrayList<>();
        
        setSize(500, 200);
        setLocationRelativeTo(owner);
        
        lblEnunciado = new JLabel();
        txtRespuesta = new JTextField();
        JButton btnSiguiente = new JButton("Siguiente");
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(lblEnunciado);
        add(txtRespuesta);
        add(btnSiguiente);
        
        btnSiguiente.addActionListener(e -> {
            respuestas.add(txtRespuesta.getText());
            preguntaActual++;
            
            if (preguntaActual < preguntas.size()) {
                mostrarPregunta();
            } else {
                terminada = true;
                dispose();
            }
        });
        
        mostrarPregunta();
    }
    
    private void mostrarPregunta() {
        Pregunta p = preguntas.get(preguntaActual);
        lblEnunciado.setText("Pregunta " + (preguntaActual + 1) + ": " + p.getEnunciado());
        txtRespuesta.setText("");
    }
    
    public boolean isTerminada() {
        return terminada;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }
}