import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    
    private static HashMap<String, Asignatura> mapaAsignaturas = new HashMap<>();
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatosIniciales();
        
        while (true) {
            System.out.println("\n*~*~* sistema de gestion *~*~*");
            System.out.println("asignaturas:");
            for (String codigo : mapaAsignaturas.keySet()) {
                System.out.println("-> " + codigo);
            }
            System.out.println("...................................");
            System.out.print("ingrese codigo asignatura (o 'salir'): ");
            String codigoIngresado = teclado.nextLine();
            
            if (codigoIngresado.equalsIgnoreCase("salir")) {
                System.out.println("chao jeje.");
                break;
            }
            
            Asignatura asignaturaSeleccionada = mapaAsignaturas.get(codigoIngresado.toUpperCase());
            
            if (asignaturaSeleccionada != null) {
                gestionarAsignatura(asignaturaSeleccionada);
            } else {
                System.out.println("!! codigo no existe :(  !!");
            }
        }
        teclado.close();
    }
    
    public static void cargarDatosIniciales() {
        Asignatura matematicas = new Asignatura("MAT-101", "Matematicas");
        BancoDePreguntas bancoAlgebra = new BancoDePreguntas("Algebra Basica");
        bancoAlgebra.agregarPregunta("Cuanto es 2+2?", "4", 5);
        bancoAlgebra.agregarPregunta("Resolver x + 5 = 10", "x=5", 10);
        matematicas.agregarBancoDePreguntas(bancoAlgebra);
        
        BancoDePreguntas bancoGeometria = new BancoDePreguntas("Geometria");
        bancoGeometria.agregarPregunta("Formula del area de un circulo?", "pi*r^2", 10);
        matematicas.agregarBancoDePreguntas(bancoGeometria);
        
        Asignatura programacion = new Asignatura("INF-2236", "Programacion Avanzada");
        BancoDePreguntas bancoPoo = new BancoDePreguntas("Conceptos de POO");
        bancoPoo.agregarPregunta("Que es encapsulamiento?", "Ocultar datos", 15);
        programacion.agregarBancoDePreguntas(bancoPoo);
        
        mapaAsignaturas.put(matematicas.getCodigo(), matematicas);
        mapaAsignaturas.put(programacion.getCodigo(), programacion);
    }
    
    public static void gestionarAsignatura(Asignatura asignatura) {
        while (true) {
            System.out.println("\n>> Gestionando: " + asignatura.getNombre());
            System.out.println("1. agregar banco nuevo");
            System.out.println("2. mostrar bancos");
            System.out.println("3. ver preguntas de un banco");
            System.out.println("4. agregar pregunta a un banco");
            System.out.println("5. volver atras");
            System.out.print("opcion: ");
            String opcion = teclado.nextLine();
            
            switch (opcion) {
                case "1":
                    agregarBanco(asignatura);
                    break;
                case "2":
                    mostrarBancos(asignatura);
                    break;
                case "3":
                    mostrarPreguntasDeBanco(asignatura);
                    break;
                case "4":
                    agregarPreguntaABanco(asignatura);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("!! opcion no valida D: !!");
                    break;
            }
        }
    }
    
    public static void agregarBanco(Asignatura asignatura) {
        System.out.print("tema del nuevo banco: ");
        String tema = teclado.nextLine();
        
        if (tema.trim().isEmpty()) {
            System.out.println("el tema no puede estar vacio.");
            return;
        }

        BancoDePreguntas nuevoBanco = new BancoDePreguntas(tema);
        asignatura.agregarBancoDePreguntas(nuevoBanco);
        
        System.out.println("-> banco '" + tema + "' agregado.");
    }
    
    public static void mostrarBancos(Asignatura asignatura) {
        System.out.println("\nBancos para '" + asignatura.getNombre() + "':");
        ArrayList<BancoDePreguntas> bancos = asignatura.getListaBancosDePreguntas();
        
        if (bancos.isEmpty()) {
            System.out.println("-> No hay bancos registrados.");
        } else {
            for (int i = 0; i < bancos.size(); i++) {
                BancoDePreguntas banco = bancos.get(i);
                System.out.println((i + 1) + ") Tema: " + banco.getTema() + " (" + banco.getListaPreguntas().size() + " preguntas)");
            }
        }
    }

    public static void mostrarPreguntasDeBanco(Asignatura asignatura) {
        mostrarBancos(asignatura);
        ArrayList<BancoDePreguntas> bancos = asignatura.getListaBancosDePreguntas();

        if (bancos.isEmpty()) {
            return;
        }

        System.out.print("elija un banco por numero para ver sus preguntas: ");
        try {
            int seleccion = Integer.parseInt(teclado.nextLine());
            if (seleccion >= 1 && seleccion <= bancos.size()) {
                BancoDePreguntas bancoSeleccionado = bancos.get(seleccion - 1);
                ArrayList<Pregunta> preguntas = bancoSeleccionado.getListaPreguntas();
                System.out.println("\n--- Preguntas del banco '" + bancoSeleccionado.getTema() + "' ---");
                if (preguntas.isEmpty()){
                    System.out.println("-> Este banco no tiene preguntas.");
                } else {
                    for (Pregunta p : preguntas) {
                        System.out.println("   - P: " + p.getEnunciado() + " | R: " + p.getRespuestaCorrecta() + " | Pts: " + p.getPuntaje());
                    }
                }
            } else {
                System.out.println("!! numero fuera de rango !!");
            }
        } catch (NumberFormatException e) {
            System.out.println("!! eso no es un numero !!");
        }
    }

    public static void agregarPreguntaABanco(Asignatura asignatura) {
        mostrarBancos(asignatura);
        ArrayList<BancoDePreguntas> bancos = asignatura.getListaBancosDePreguntas();

        if (bancos.isEmpty()) {
            return;
        }

        System.out.print("elija un banco por numero para agregarle una pregunta: ");
        try {
            int seleccion = Integer.parseInt(teclado.nextLine());
            if (seleccion >= 1 && seleccion <= bancos.size()) {
                BancoDePreguntas bancoSeleccionado = bancos.get(seleccion - 1);
                
                System.out.print("enunciado de la nueva pregunta: ");
                String enunciado = teclado.nextLine();
                System.out.print("respuesta correcta: ");
                String respuesta = teclado.nextLine();
                System.out.print("puntaje: ");
                int puntaje = Integer.parseInt(teclado.nextLine());
                
                bancoSeleccionado.agregarPregunta(enunciado, respuesta, puntaje);
                System.out.println("-> pregunta agregada al banco '" + bancoSeleccionado.getTema() + "'.");

            } else {
                System.out.println("!! numero fuera de rango !!");
            }
        } catch (NumberFormatException e) {
            System.out.println("!! error, se esperaba un numero !!");
        }
    }
}