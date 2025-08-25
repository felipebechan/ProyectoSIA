import java.util.ArrayList;

public class BancoDePreguntas {
    private String tema;
    private ArrayList<Pregunta> listaPreguntas;

    // constructor
    public BancoDePreguntas(String tema) {
        this.tema = tema;
        this.listaPreguntas = new ArrayList<>();
    }

    // getters y setters
    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public ArrayList<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(ArrayList<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
    
    // metodos para agregar preguntas 
    //sobrecraga
    public void agregarPregunta(Pregunta nuevaPregunta) {
        this.listaPreguntas.add(nuevaPregunta);
    }

    public void agregarPregunta(String enunciado, String respuesta, int puntaje) {
        Pregunta nuevaPregunta = new Pregunta(enunciado, respuesta, puntaje);
        this.listaPreguntas.add(nuevaPregunta);
    }
}