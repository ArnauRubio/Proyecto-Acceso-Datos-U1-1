package com.videoteca.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestamo implements Serializable {
    private int id;
    private int usuarioId;
    private int videojuegoId;
    private String fecha;
    private boolean devuelto;

    public Prestamo() {
    }

    public Prestamo(int id, int usuarioId, int videojuegoId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.videojuegoId = videojuegoId;
        this.fecha = LocalDate.now().toString();
        this.devuelto = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getVideojuegoId() {
        return videojuegoId;
    }

    public void setVideojuegoId(int videojuegoId) {
        this.videojuegoId = videojuegoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return String.format("[%d] Usuario=%d | Videojuego=%d | Fecha=%s | %s", id, usuarioId, videojuegoId, fecha, devuelto ? "DEVUELTO" : "PENDIENTE");
    }
}
