/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.vista;

import PackProyecto.controlador.AppController;
import PackProyecto.modelo.Alumno;
import PackProyecto.modelo.Asignatura;
import PackProyecto.modelo.Persona;
import PackProyecto.modelo.Profesor;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author mac
 */
public class VentanaPrincipal extends JFrame {
    private AppController controlador; 
    private DefaultListModel<String> modeloLista;
    private JButton btnAddAsig, btnModAsig, btnDelAsig, btnVerBancos, btnTomarPrueba;
    private JButton btnAdminUsers, btnVerProm, btnExportar, btnGuardar;

    public VentanaPrincipal(AppController controlador) {
        this.controlador = controlador;
        setTitle("Sistema de Gestion");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(null);

        // panel izq
        JLabel lblAsignaturas = new JLabel("Asignaturas:");
        lblAsignaturas.setBounds(10, 10, 100, 25);
        panel.add(lblAsignaturas);
        modeloLista = new DefaultListModel<>();
        JList<String> listaAsignaturasUI = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaAsignaturasUI);
        scrollPane.setBounds(10, 40, 300, 350);
        panel.add(scrollPane);

        // panel der
        btnAddAsig = new JButton("Agregar Asignatura");
        btnAddAsig.setBounds(320, 40, 150, 25);
        panel.add(btnAddAsig);
        btnModAsig = new JButton("Modificar Asignatura");
        btnModAsig.setBounds(320, 75, 150, 25);
        panel.add(btnModAsig);
        btnDelAsig = new JButton("Eliminar Asignatura");
        btnDelAsig.setBounds(320, 110, 150, 25);
        panel.add(btnDelAsig);
        btnVerBancos = new JButton("Gestionar Bancos");
        btnVerBancos.setBounds(320, 145, 150, 25);
        panel.add(btnVerBancos);
        btnTomarPrueba = new JButton("Tomar Prueba");
        btnTomarPrueba.setBounds(320, 180, 150, 25);
        panel.add(btnTomarPrueba);
        
        btnAdminUsers = new JButton("Admin. Usuarios");
        btnAdminUsers.setBounds(480, 40, 150, 25);
        panel.add(btnAdminUsers);
        btnVerProm = new JButton("Ver Promedios");
        btnVerProm.setBounds(480, 75, 150, 25);
        panel.add(btnVerProm);
        btnExportar = new JButton("Exportar Notas");
        btnExportar.setBounds(480, 110, 150, 25);
        panel.add(btnExportar);
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBounds(480, 365, 150, 25);
        panel.add(btnGuardar);

        add(panel);
        actualizarListaAsignaturas();

        // listeners
        btnAddAsig.addActionListener(e -> {
            String c = JOptionPane.showInputDialog(this, "codigo:");
            if (c != null && !c.trim().isEmpty()) {
                String n = JOptionPane.showInputDialog(this, "nombre:");
                if (n != null && !n.trim().isEmpty()) {
                    controlador.agregarAsignatura(c, n);
                    actualizarListaAsignaturas();
                }
            }
        });
        btnModAsig.addActionListener(e -> {
            int s = listaAsignaturasUI.getSelectedIndex();
            if (s != -1) {
                Asignatura a = controlador.getAsignaturas().get(s);
                String nc = JOptionPane.showInputDialog(this, "nuevo codigo:", a.getCodigo());
                if (nc != null && !nc.trim().isEmpty()) {
                    String nn = JOptionPane.showInputDialog(this, "nuevo nombre:", a.getNombre());
                    if (nn != null && !nn.trim().isEmpty()) {
                        controlador.modificarAsignatura(a, nc, nn);
                        actualizarListaAsignaturas();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "selecciona una asignatura.", "ojo", 2);
            }
        });
        btnDelAsig.addActionListener(e -> {
            int s = listaAsignaturasUI.getSelectedIndex();
            if (s != -1) {
                Asignatura a = controlador.getAsignaturas().get(s);
                int c = JOptionPane.showConfirmDialog(this, "seguro que quieres eliminar '" + a.getNombre() + "'?", "confirmar", 0);
                if (c == 0) {
                    controlador.eliminarAsignatura(a);
                    actualizarListaAsignaturas();
                }
            } else {
                JOptionPane.showMessageDialog(this, "selecciona una asignatura.", "ojo", 2);
            }
        });
        
        btnVerBancos.addActionListener(e -> {
            int s = listaAsignaturasUI.getSelectedIndex();
            if (s != -1) controlador.abrirVentanaBancos(controlador.getAsignaturas().get(s));
            else JOptionPane.showMessageDialog(this, "selecciona una asignatura.", "ojo", 2);
        });

        btnTomarPrueba.addActionListener(e -> controlador.iniciarProcesoDePrueba());
        btnAdminUsers.addActionListener(e -> controlador.mostrarVentanaUsuarios());
        btnVerProm.addActionListener(e -> controlador.verPromedios());
        btnExportar.addActionListener(e -> controlador.exportarNotas());
        btnGuardar.addActionListener(e -> controlador.guardarTodo());
    }

    private void actualizarListaAsignaturas() {
        modeloLista.clear();
        for (Asignatura a : controlador.getAsignaturas()) {
            modeloLista.addElement(a.getCodigo() + " - " + a.getNombre());
        }
    }

    public void configurarParaUsuario(Persona usuario) {
        boolean esProfe = usuario instanceof Profesor;
        
        setTitle(getTitle() + " - Bienvenido " + (esProfe ? "Profesor: " : "Alumno: ") + usuario.getNombre());
        
        // botones de profe
        btnAddAsig.setEnabled(esProfe);
        btnModAsig.setEnabled(esProfe);
        btnDelAsig.setEnabled(esProfe);
        btnVerBancos.setEnabled(esProfe);
        btnAdminUsers.setEnabled(esProfe);
        btnVerProm.setEnabled(esProfe);
        btnExportar.setEnabled(esProfe);
        
        // boton de alumno
        btnTomarPrueba.setEnabled(!esProfe);
    }
}