/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackProyecto.controlador;

import PackProyecto.modelo.*;
import PackProyecto.vista.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;


/**
 *
 * @author mac
 */
public class AppController {
    private SistemaEvaluaciones modelo;
    private Persona usuarioActual;
    private VentanaLogin vistaLogin;
    private VentanaPrincipal vistaPrincipal;

    public AppController(SistemaEvaluaciones modelo) {
        this.modelo = modelo;
    }
    
    public void iniciar() {
        vistaLogin = new VentanaLogin(this);
        vistaLogin.setVisible(true);
    }
    
    public void intentarLogin(String rut) {
        usuarioActual = modelo.validarUsuario(rut);
        if (usuarioActual != null) {
            vistaLogin.dispose();
            vistaPrincipal = new VentanaPrincipal(this);
            vistaPrincipal.configurarParaUsuario(usuarioActual);
            vistaPrincipal.setVisible(true);
        } else {
            vistaLogin.mostrarError("rut no encontrado. intenta de nuevo.");
        }
    }
    
    public List<Asignatura> getAsignaturas() { return modelo.getListaAsignaturas(); }
    public List<Persona> getUsuarios() { return modelo.getListaUsuarios(); }
    
    public void agregarAsignatura(String codigo, String nombre) {
        try {
            modelo.agregarAsignatura(new Asignatura(codigo, nombre));
            JOptionPane.showMessageDialog(vistaPrincipal, "asignatura agregada bien.", "listo", 1);
        } catch (ElementoDuplicadoException ex) {
            JOptionPane.showMessageDialog(vistaPrincipal, ex.getMessage(), "error", 0);
        }
    }
    
    public void modificarAsignatura(Asignatura asigActual, String nuevoCodigo, String nuevoNombre) {
        try {
            modelo.modificarAsignatura(asigActual.getCodigo(), nuevoCodigo, nuevoNombre);
            JOptionPane.showMessageDialog(vistaPrincipal, "asignatura modificada.", "listo", 1);
        } catch (ElementoNoEncontradoException | ElementoDuplicadoException ex) {
            JOptionPane.showMessageDialog(vistaPrincipal, ex.getMessage(), "error", 0);
        }
    }
    
    public void eliminarAsignatura(Asignatura asigAEliminar) {
        try {
            modelo.eliminarAsignatura(asigAEliminar.getCodigo());
            JOptionPane.showMessageDialog(vistaPrincipal, "asignatura eliminada.", "listo", 1);
        } catch (ElementoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(vistaPrincipal, ex.getMessage(), "error", 0);
        }
    }
    
    public void mostrarVentanaUsuarios() {
        new VentanaUsuarios(vistaPrincipal, this).setVisible(true);
    }
    
    public void agregarNuevoAlumno(String rut, String nombre, String curso) {
        try {
            modelo.agregarUsuario(new Alumno(nombre, rut, curso));
            JOptionPane.showMessageDialog(null, "alumno agregado", "listo", 1);
        } catch (ElementoDuplicadoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "error", 0);
        }
    }
    
    public void agregarNuevoProfesor(String rut, String nombre, String especialidad) {
        try {
            modelo.agregarUsuario(new Profesor(nombre, rut, especialidad));
            JOptionPane.showMessageDialog(null, "profesor agregado", "listo", 1);
        } catch (ElementoDuplicadoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "error", 0);
        }
    }
    
    public void verPromedios() {
        String reporte = modelo.generarReportePromedios();
        JTextArea textArea = new JTextArea(reporte);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(vistaPrincipal, scrollPane, "Reporte de Promedios", 1);
    }
    
    public void exportarNotas() {
        try {
            modelo.exportarNotasCSV("exportacion_notas.csv");
            JOptionPane.showMessageDialog(vistaPrincipal, "notas exportadas en 'exportacion_notas.csv'", "listo", 1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vistaPrincipal, "hubo un error al exportar.", "error", 0);
        }
    }
    
    public void guardarTodo() {
        modelo.guardarDatos();
        JOptionPane.showMessageDialog(vistaPrincipal, "datos guardados.", "guardado", 1);
    }
    
    public void abrirVentanaBancos(Asignatura asig) {
        if (asig != null) {
            new VentanaBancos(vistaPrincipal, asig).setVisible(true);
        }
    }
    
    public void generarReporte() {
        try {
            modelo.generarReporteAsignaturas("reporte.txt");
            JOptionPane.showMessageDialog(vistaPrincipal, "reporte generado en 'reporte.txt'", "listo", 1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vistaPrincipal, "hubo un error al generar el reporte.", "error", 0);
        }
    }
    
    public void iniciarProcesoDePrueba() {
        if (!(usuarioActual instanceof Alumno)) {
            JOptionPane.showMessageDialog(vistaPrincipal, "esta funcion es solo para alumnos", "error", 0);
            return;
        }
        
        List<Asignatura> asignaturas = modelo.getListaAsignaturas();
        if (asignaturas.isEmpty()) {
            JOptionPane.showMessageDialog(vistaPrincipal, "no hay asignaturas para dar pruebas", "aviso", 1);
            return;
        }
        
        String[] nombresAsignaturas = asignaturas.stream().map(Asignatura::getNombre).toArray(String[]::new);
        String nombreElegido = (String) JOptionPane.showInputDialog(vistaPrincipal, "elige una asignatura", "seleccionar prueba", 3, null, nombresAsignaturas, nombresAsignaturas[0]);
        
        if (nombreElegido == null) return;
        
        Asignatura asignaturaElegida = asignaturas.stream().filter(a -> a.getNombre().equals(nombreElegido)).findFirst().orElse(null);
        int numPreguntas;
        
        try {
            numPreguntas = Integer.parseInt(JOptionPane.showInputDialog(vistaPrincipal, "cuantas preguntas quieres? (max 10)", "5"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaPrincipal, "eso no es un numero valido.", "error", 0);
            return;
        }
        
        Evaluacion prueba = modelo.crearPrueba(asignaturaElegida, (Alumno) usuarioActual, numPreguntas);
        
        if (prueba == null || prueba.getPreguntas().isEmpty()) {
            JOptionPane.showMessageDialog(vistaPrincipal, "no se pudo crear la prueba", "error", 0);
            return;
        }
        
        VentanaTomarPrueba vistaPrueba = new VentanaTomarPrueba(vistaPrincipal, prueba.getPreguntas());
        vistaPrueba.setVisible(true);
        
        if (vistaPrueba.isTerminada()) {
            Nota notaFinal = modelo.corregirPrueba(prueba, vistaPrueba.getRespuestas());
            JOptionPane.showMessageDialog(vistaPrincipal, usuarioActual.getNombre() + ", tu nota final es: " + notaFinal.getNota(), "resultado", 1);
        }
    }
}