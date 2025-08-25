public class Pregunta {
    private String enunciado;
    private String respuestaCorrecta;
    private int puntaje;

    // constructor de la pregunta
    public Pregunta(String enunciado, String respuestaCorrecta, int puntaje) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.puntaje = puntaje;
    }

    // getters y setters para los atributos
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}