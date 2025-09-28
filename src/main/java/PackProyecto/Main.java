/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

import PackProyecto.controlador.AppController;
import PackProyecto.modelo.SistemaEvaluaciones;
import javax.swing.SwingUtilities;

/**
 *
 * @author mac
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaEvaluaciones modelo = new SistemaEvaluaciones();
            modelo.cargarDatos();
            
            AppController controlador = new AppController(modelo);
            controlador.iniciar();
        });
    }
}