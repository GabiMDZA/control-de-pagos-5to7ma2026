package com.redtel.controldepagos.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "velocidades")
public class Velocidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cantidad; //en Mbps 
    private int precio;

    public Velocidad() {}

    public Velocidad(int cantidad, int precio) {
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }
}