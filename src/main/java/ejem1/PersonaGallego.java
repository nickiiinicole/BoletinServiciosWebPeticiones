package ejem1;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonaGallego implements Serializable {
    private int id;
    private String nome;
    private boolean casado;
    private String xenero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nombre) {
        this.nome = nombre;
    }

    public boolean isCasado() {
        return casado;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    public String getXenero() {
        return xenero;
    }

    public void setXenero(String sexo) {
        this.xenero = sexo;
    }

    public PersonaGallego(int id, String nome, boolean casado, String sexo) {
        this.id = id;
        this.nome = nome;
        this.casado = casado;
        this.xenero = sexo;
    }

    public PersonaGallego() {
    }
}