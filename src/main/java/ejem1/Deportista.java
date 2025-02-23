package ejem1;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Deportista {

    int id;
    String nombre;
    String deporte;
    boolean activo;
    String genero;

    public Deportista() {

    }
    public Deportista(int id, String nombre, String deporte, boolean activo, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.deporte = deporte;
        this.activo = activo;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String cadena) {
        this.genero = cadena;
    }

}
