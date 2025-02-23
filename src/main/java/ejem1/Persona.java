package ejem1;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAttribute;

//para que se pueda serializar como un recurso XML / JSON

@XmlRootElement
public class Persona {

    private int id;
    private String nombre;
    private boolean casado;
    private String sexo;

    //constructor vacio para persona 
    public Persona() {
    }

    public Persona(int id, String nombre, boolean casado, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.casado = casado;
        this.sexo = sexo;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // @XmlElement(name = "nome") // Nombre del campo en gallego

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isCasado() {
        return casado;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    // @XmlElement(name = "xénero") // "sexo" en gallego
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", casado=" + casado +
                ", sexo='" + sexo + '\'' +
                '}';
    }

}
