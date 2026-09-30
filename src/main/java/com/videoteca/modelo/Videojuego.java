package com.videoteca.modelo;

import java.io.Serializable;

public class Videojuego implements Serializable {
    private int id;
    private String titulo;
    private String plataforma;
    private int anio;
    private double precio;
    private boolean activo;

    public Videojuego() {
    }

    public Videojuego(int id, String titulo, String plataforma, int anio, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.anio = anio;
        this.precio = precio;
        this.activo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %d | %.2f € | %s", id, titulo, plataforma, anio, precio, activo ? "ACTIVO" : "BAJA");
    }
}
