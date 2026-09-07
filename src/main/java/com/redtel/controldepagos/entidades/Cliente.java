package com.redtel.controldepagos.entidades;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String celular;
    private String direccion;
    private boolean pagó;
    private String velocidad;
    private int montoPaga;

    // Constructores
    public Cliente() {}

    public Cliente(String nombre, String apellido, String celular, String direccion, boolean pagó, String velocidad, int montoPaga) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.direccion = direccion;
        this.pagó = pagó;
        this.velocidad = velocidad;
        this.montoPaga = montoPaga;
    }

    // Getters y Setters

    //ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Apellido
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //Celular
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    //Direccion
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //Pagó
    public boolean isPagó() {
        return pagó;
    }

    public void setPagó(boolean pagó) {
        this.pagó = pagó;
    }

    //Velocidad
    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    //Monto a Pagar
    public int getMontoPaga() {
        return montoPaga;
    }

    public void setMontoPaga(int montoPaga) {
        this.montoPaga = montoPaga;
    }
}
