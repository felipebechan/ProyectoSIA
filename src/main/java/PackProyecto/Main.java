/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

import javax.swing.SwingUtilities;

/**
 *
 * @author mac
 */
public class Main {
    public static void main(String[] args) {
        // para asegurar que la gui se ejecute bien
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // creamos el sistema que contiene toda la logica
                SistemaEvaluaciones sistema = new SistemaEvaluaciones();
                
                // SIA2.2: cargamos los datos al iniciar la aplicacion
                sistema.cargarDatos(); 
                
                // creamos y mostramos la ventana principal, pasandole el sistema
                new VentanaPrincipal(sistema).setVisible(true);
            }
        });
    }
}