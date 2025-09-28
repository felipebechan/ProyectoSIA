/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author mac
 */
public class VentanaPrincipal extends JFrame {
    private SistemaEvaluaciones sistema;
    private JList<String> listaAsignaturasUI;
    private DefaultListModel<String> modeloLista;

    public VentanaPrincipal(SistemaEvaluaciones sistema) {
        this.sistema = sistema;
        setTitle("Sistema de Gestion de Evaluaciones");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // para controlar el cierre
        setLocationRelativeTo(null);

        // el panel principal
        JPanel panel = new JPanel(null);
        
        JLabel lblAsignaturas = new JLabel("Asignaturas:");
        lblAsignaturas.setBounds(10, 10, 100, 25);
        panel.add(lblAsignaturas);

        modeloLista = new DefaultListModel<>();
        listaAsignaturasUI = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaAsignaturasUI);
        scrollPane.setBounds(10, 40, 300, 300);
        panel.add(scrollPane);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(320, 40, 150, 25);
        panel.add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(320, 75, 150, 25);
        panel.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(320, 110, 150, 25);
        panel.add(btnEliminar);
        
        JButton btnVerBancos = new JButton("Gestionar Bancos");
        btnVerBancos.setBounds(320, 145, 150, 25);
        panel.add(btnVerBancos);
        
        JButton btnReporte = new JButton("Generar Reporte");
        btnReporte.setBounds(320, 180, 150, 25);
        panel.add(btnReporte);

        add(panel);
        actualizarListaAsignaturas();

        // ACCIONES DE LOS BOTONES

        btnAgregar.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this, "ingresa codigo de la asignatura:");
            if (codigo == null || codigo.trim().isEmpty()) return;
            String nombre = JOptionPane.showInputDialog(this, "ingresa nombre de la asignatura:");
            if (nombre == null || nombre.trim().isEmpty()) return;

            try {
                sistema.agregarAsignatura(new Asignatura(codigo, nombre));
                actualizarListaAsignaturas();
                JOptionPane.showMessageDialog(this, "asignatura agregada bien.", "listo", JOptionPane.INFORMATION_MESSAGE);
            } catch (ElementoDuplicadoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error de duplicado", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnModificar.addActionListener(e -> {
            int seleccionado = listaAsignaturasUI.getSelectedIndex();
            if (seleccionado != -1) {
                Asignatura asigActual = sistema.getListaAsignaturas().get(seleccionado);
                String nuevoCodigo = JOptionPane.showInputDialog(this, "ingresa el nuevo codigo:", asigActual.getCodigo());
                if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) return;
                String nuevoNombre = JOptionPane.showInputDialog(this, "ingresa el nuevo nombre:", asigActual.getNombre());
                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;

                try {
                    sistema.modificarAsignatura(asigActual.getCodigo(), nuevoCodigo, nuevoNombre);
                    actualizarListaAsignaturas();
                    JOptionPane.showMessageDialog(this, "asignatura modificada.", "listo", JOptionPane.INFORMATION_MESSAGE);
                } catch (ElementoNoEncontradoException | ElementoDuplicadoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "primero selecciona una asignatura.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int seleccionado = listaAsignaturasUI.getSelectedIndex();
            if (seleccionado != -1) {
                Asignatura asigAEliminar = sistema.getListaAsignaturas().get(seleccionado);
                int confirm = JOptionPane.showConfirmDialog(this, "seguro que quieres eliminar la asignatura '" + asigAEliminar.getNombre() + "'?", "confirmar", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        sistema.eliminarAsignatura(asigAEliminar.getCodigo());
                        actualizarListaAsignaturas();
                        JOptionPane.showMessageDialog(this, "asignatura eliminada.", "listo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ElementoNoEncontradoException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "primero selecciona una asignatura para eliminar.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnVerBancos.addActionListener(e -> {
            int seleccionado = listaAsignaturasUI.getSelectedIndex();
            if (seleccionado != -1) {
                Asignatura asig = sistema.getListaAsignaturas().get(seleccionado);
                new VentanaBancos(this, asig).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "selecciona una asignatura.", "ojo", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnReporte.addActionListener(e -> {
            sistema.generarReporteAsignaturas("reporte_asignaturas.txt");
            JOptionPane.showMessageDialog(this, "reporte generado en 'reporte_asignaturas.txt'", "listo", JOptionPane.INFORMATION_MESSAGE);
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "quieres guardar los cambios y salir?", "salir", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    sistema.guardarDatos();
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void actualizarListaAsignaturas() {
        modeloLista.clear();
        for (Asignatura a : sistema.getListaAsignaturas()) {
            modeloLista.addElement(a.getCodigo() + " - " + a.getNombre());
        }
    }
}