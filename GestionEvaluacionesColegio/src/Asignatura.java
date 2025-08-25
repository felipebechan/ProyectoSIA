import java.util.ArrayList;

public class Asignatura {
    private String codigo;
    private String nombre;
    private ArrayList<BancoDePreguntas> listaBancosDePreguntas; // coleccion anidada

    // constructor
    public Asignatura(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.listaBancosDePreguntas = new ArrayList<>();
    }

    // getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<BancoDePreguntas> getListaBancosDePreguntas() {
        return listaBancosDePreguntas;
    }

    public void setListaBancosDePreguntas(ArrayList<BancoDePreguntas> listaBancosDePreguntas) {
        this.listaBancosDePreguntas = listaBancosDePreguntas;
    }
    
    // metodo para anadir un banco a la lista
    public void agregarBancoDePreguntas(BancoDePreguntas banco) {
        this.listaBancosDePreguntas.add(banco);
    }
}