/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.vista;

import PackProyecto.controlador.AppController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mac
 */
public class VentanaLogin extends JFrame {
    private AppController controlador;
    private JTextField txtRut;
    
    public VentanaLogin(AppController controlador) {
        this.controlador = controlador;
        setTitle("Login de Usuario");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        
        panel.add(new JLabel("ingresa tu rut (ej: ALM: 1-1 o  PROF: 2-2):"));
        
        txtRut = new JTextField();
        panel.add(txtRut);
        
        JButton btnEntrar = new JButton("Entrar");
        panel.add(btnEntrar);
        
        add(panel);
        
        btnEntrar.addActionListener(e -> {
            controlador.intentarLogin(txtRut.getText());
        });
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "error de login", JOptionPane.ERROR_MESSAGE);
    }
}