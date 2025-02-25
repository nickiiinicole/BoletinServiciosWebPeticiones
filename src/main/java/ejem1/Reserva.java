package ejem1;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class Reserva implements Serializable {
    private int id;
    private String cliente;
    private int habitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private String estado;

    public Reserva() {
    }

    public Reserva(int id, String cliente, int habitacion, Date fechaEntrada, Date fechaSalida, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{id=" + id + ", cliente='" + cliente + "', habitacion=" + habitacion +
                ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + ", estado='" + estado + "'}";
    }
}
