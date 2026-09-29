package com.videoteca.service;

import com.videoteca.dao.FicheroObjetos;
import com.videoteca.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class VideotecaService {
    private final FicheroObjetos<Videojuego> fVideojuegos = new FicheroObjetos<>("data/videojuegos.dat");
    private final FicheroObjetos<Usuario> fUsuarios = new FicheroObjetos<>("data/usuarios.dat");
    private final FicheroObjetos<Prestamo> fPrestamos = new FicheroObjetos<>("data/prestamos.dat");

    public List<Videojuego> videojuegos() throws Exception {
        return fVideojuegos.leer();
    }

    public List<Usuario> usuarios() throws Exception {
        return fUsuarios.leer();
    }

    public List<Prestamo> prestamos() throws Exception {
        return fPrestamos.leer();
    }

    public void altaVideojuego(Videojuego v) throws Exception {
        List<Videojuego> l = videojuegos();
        if (l.stream().anyMatch(x -> x.getId() == v.getId()))
            throw new IllegalArgumentException("Ya existe ese ID de videojuego.");
        l.add(v);
        fVideojuegos.escribir(l);
    }

    public void modificarVideojuego(Videojuego v) throws Exception {
        List<Videojuego> l = videojuegos();
        Videojuego actual = buscarVideojuego(l, v.getId()).orElseThrow(() -> new IllegalArgumentException("No existe el videojuego."));
        actual.setTitulo(v.getTitulo());
        actual.setPlataforma(v.getPlataforma());
        actual.setAnio(v.getAnio());
        actual.setPrecio(v.getPrecio());
        fVideojuegos.escribir(l);
    }

    public void bajaVideojuego(int id) throws Exception {
        List<Videojuego> l = videojuegos();
        Videojuego v = buscarVideojuego(l, id).orElseThrow(() -> new IllegalArgumentException("No existe el videojuego."));
        if (prestamos().stream().anyMatch(p -> p.getVideojuegoId() == id && !p.isDevuelto()))
            throw new IllegalArgumentException("No se puede dar de baja: tiene un préstamo pendiente.");
        v.setActivo(false);
        fVideojuegos.escribir(l);
    }

    public void altaUsuario(Usuario u) throws Exception {
        List<Usuario> l = usuarios();
        if (l.stream().anyMatch(x -> x.getId() == u.getId()))
            throw new IllegalArgumentException("Ya existe ese ID de usuario.");
        l.add(u);
        fUsuarios.escribir(l);
    }

    public void modificarUsuario(Usuario u) throws Exception {
        List<Usuario> l = usuarios();
        Usuario actual = buscarUsuario(l, u.getId()).orElseThrow(() -> new IllegalArgumentException("No existe el usuario."));
        actual.setNombre(u.getNombre());
        actual.setEmail(u.getEmail());
        fUsuarios.escribir(l);
    }

    public void bajaUsuario(int id) throws Exception {
        List<Usuario> l = usuarios();
        Usuario u = buscarUsuario(l, id).orElseThrow(() -> new IllegalArgumentException("No existe el usuario."));
        if (prestamos().stream().anyMatch(p -> p.getUsuarioId() == id && !p.isDevuelto()))
            throw new IllegalArgumentException("No se puede dar de baja: tiene un préstamo pendiente.");
        u.setActivo(false);
        fUsuarios.escribir(l);
    }

    public void altaPrestamo(Prestamo p) throws Exception {
        List<Usuario> us = usuarios();
        List<Videojuego> vs = videojuegos();
        List<Prestamo> ps = prestamos();
        Usuario u = buscarUsuario(us, p.getUsuarioId()).orElseThrow(() -> new IllegalArgumentException("El usuario no existe."));
        Videojuego v = buscarVideojuego(vs, p.getVideojuegoId()).orElseThrow(() -> new IllegalArgumentException("El videojuego no existe."));
        if (!u.isActivo() || !v.isActivo()) throw new IllegalArgumentException("Usuario o videojuego dado de baja.");
        if (ps.stream().anyMatch(x -> x.getId() == p.getId()))
            throw new IllegalArgumentException("Ya existe ese ID de préstamo.");
        if (ps.stream().anyMatch(x -> x.getVideojuegoId() == p.getVideojuegoId() && !x.isDevuelto()))
            throw new IllegalArgumentException("El videojuego ya está prestado.");
        ps.add(p);
        fPrestamos.escribir(ps);
    }

    public void devolverPrestamo(int id) throws Exception {
        List<Prestamo> l = prestamos();
        Prestamo p = l.stream().filter(x -> x.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("No existe el préstamo."));
        p.setDevuelto(true);
        fPrestamos.escribir(l);
    }

    private Optional<Videojuego> buscarVideojuego(List<Videojuego> l, int id) {
        return l.stream().filter(v -> v.getId() == id).findFirst();
    }

    private Optional<Usuario> buscarUsuario(List<Usuario> l, int id) {
        return l.stream().filter(u -> u.getId() == id).findFirst();
    }

    public BaseDatos cargarTodo() throws Exception {
        BaseDatos db = new BaseDatos();
        db.setVideojuegos(videojuegos());
        db.setUsuarios(usuarios());
        db.setPrestamos(prestamos());
        return db;
    }

    public void guardarTodo(BaseDatos db) throws IOException {
        fVideojuegos.escribir(db.getVideojuegos());
        fUsuarios.escribir(db.getUsuarios());
        fPrestamos.escribir(db.getPrestamos());
    }
}
