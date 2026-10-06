package com.videoteca.Acciones;

import com.videoteca.dao.FicheroObjetos;
import com.videoteca.modelo.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class VideotecaAcciones {
    /**
     * Se crean los ficheros de la aplicacion y unas listas para almacenar su informacion
     */
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

    /**
     * Funcion para crear un nuevo videojuego
     * @param v el videojuego en cuestion
     * @throws Exception en caso de que ya exista un juego con ese mismo nombre
     */

    public void crearVideojuego(Videojuego v) throws Exception {

        List<Videojuego> lista = videojuegos();

        for (Videojuego videojuego : lista) {
            if (videojuego.getId() == v.getId()) {
                throw new IllegalArgumentException("Ya existe ese ID de videojuego.");
            }
        }

        lista.add(v);

        fVideojuegos.escribir(lista);
    }

    /**
     * Funcion para modificar videjuegos
     * @param v el videojeugo a modificar
     * @throws Exception en caso de que no exista ningun juego con el nombre por el que se pidio
     */
    public void modificarVideojuego(Videojuego v) throws Exception {

        List<Videojuego> lista = videojuegos();

        for (Videojuego videojuego : lista) {

            if (videojuego.getId() == v.getId()) {

                videojuego.setTitulo(v.getTitulo());
                videojuego.setPlataforma(v.getPlataforma());
                videojuego.setAnio(v.getAnio());
                videojuego.setPrecio(v.getPrecio());

                fVideojuegos.escribir(lista);

                return;
            }
        }

        throw new IllegalArgumentException("No existe el videojuego.");
    }

    /**
     * Funcion para borrar videojuegos, ademas busca por prestamos activos
     * @param id el numero de cada juego, para buscarlo
     * @throws Exception en caso de no exista el juego o este en un prestamo activo
     */
    public void borrarVideojuego(int id) throws Exception {

        List<Videojuego> lista = videojuegos();

        Videojuego videojuegoEncontrado = null;

        for (Videojuego videojuego : lista) {

            if (videojuego.getId() == id) {
                videojuegoEncontrado = videojuego;
                break;
            }
        }

        if (videojuegoEncontrado == null) {
            throw new IllegalArgumentException("No existe el videojuego.");
        }

        for (Prestamo prestamo : prestamos()) {

            if (prestamo.getVideojuegoId() == id && !prestamo.isDevuelto()) {
                throw new IllegalArgumentException(
                        "No se puede dar de baja: tiene un préstamo pendiente."
                );
            }
        }

        videojuegoEncontrado.setActivo(false);

        fVideojuegos.escribir(lista);
    }

    /**
     * FUncion para crear usuarios
     * @param u el ususario que se crea
     * @throws Exception en caso de que ya se haya creado ese usuario
     */
    public void crearUsuario(Usuario u) throws Exception {

        List<Usuario> l = usuarios();

        for (Usuario usuario : l) {
            if (usuario.getId() == u.getId()) {
                throw new IllegalArgumentException("Ya existe ese ID de usuario.");
            }
        }

        l.add(u);

        fUsuarios.escribir(l);
    }

    /**
     * Funcion que permite modificar un usuario existente
     * @param u el ususario que se va a modificar
     * @throws Exception en caso de que no existiera el usuario que se busca
     */

    public void modificarUsuario(Usuario u) throws Exception {

        List<Usuario> l = usuarios();

        for (Usuario usuario : l) {

            if (usuario.getId() == u.getId()) {

                usuario.setNombre(u.getNombre());
                usuario.setEmail(u.getEmail());

                fUsuarios.escribir(l);

                return;
            }
        }

        throw new IllegalArgumentException("No existe el usuario.");
    }

    /**
     * Funcion para eliminar ususarios, verificando que no tengan prestamos activos
     * @param id es le numnero del ususario que se busca
     * @throws Exception en caso de que el usuario no exista o tenga prestamos pendientes
     */
    public void borrarUsuario(int id) throws Exception {

        List<Usuario> l = usuarios();

        Usuario usuarioEncontrado = null;

        for (Usuario usuario : l) {

            if (usuario.getId() == id) {
                usuarioEncontrado = usuario;
                break;
            }
        }

        if (usuarioEncontrado == null) {
            throw new IllegalArgumentException("No existe el usuario.");
        }

        for (Prestamo prestamo : prestamos()) {

            if (prestamo.getUsuarioId() == id && !prestamo.isDevuelto()) {
                throw new IllegalArgumentException(
                        "No se puede dar de baja: tiene un préstamo pendiente."
                );
            }
        }

        usuarioEncontrado.setActivo(false);

        fUsuarios.escribir(l);
    }

    /**
     * Funcion para la creacion de prestamos de juegos
     * @param p el nuevo prestamo creado
     * @throws Exception en caso de que ya existiera ese prestamo,
     * o el videojuego o usuario no existieran, o el juego en cuestion estuviese prestado ya
     */
    public void crearPrestamo(Prestamo p) throws Exception {

        List<Usuario> us = usuarios();
        List<Videojuego> vs = videojuegos();
        List<Prestamo> ps = prestamos();

        Usuario usuarioEncontrado = null;

        for (Usuario usuario : us) {
            if (usuario.getId() == p.getUsuarioId()) {
                usuarioEncontrado = usuario;
                break;
            }
        }
        if (usuarioEncontrado == null) {
            throw new IllegalArgumentException("No existe el usuario.");
        }

        Videojuego videojuegoEncontrado = null;
        for (Videojuego videojuego : vs) {
            if (videojuego.getId() == p.getVideojuegoId()) {
                videojuegoEncontrado = videojuego;
                break;
            }
        }
        if (videojuegoEncontrado == null) {
            throw new IllegalArgumentException("No existe el videojuego.");
        }

        if (!usuarioEncontrado.isActivo() || !videojuegoEncontrado.isActivo()) {
            throw new IllegalArgumentException("Usuario o videojuego dados de baja");
        }

        for (Prestamo prestamo : ps) {
            if (prestamo.getId() == p.getId()) {
                throw new IllegalArgumentException("Ya existe ese ID de prestamo.");
            }
        }

        for (Prestamo prestamo : ps) {
            if (prestamo.getVideojuegoId() == p.getVideojuegoId() && !prestamo.isDevuelto()) {
                throw new IllegalArgumentException("Ese juego ya esta prestado");
            }
        }

        prestamos().add(p);
        fPrestamos.escribir(ps);
    }

    /**
     * Funcion para la devolucion de juegos
     * @param id el numero del prestamo
     * @throws Exception en caso de que no existiera el id del prestamo
     */
    public void devolverPrestamo(int id) throws Exception {
        List<Prestamo> l = prestamos();
        for (Prestamo prestamo : l) {
            if (prestamo.getId() == id) {
                prestamo.setDevuelto(true);
                fPrestamos.escribir(l);
                return;
            }
        }
        throw new IllegalArgumentException("No existe el prestamo.");
    }

    /**
     * Funcion para la carga de informacion a la base de datos
     * @return la base de datos
     * @throws Exception en caso haya algun error al cargar la informacion
     */
    public BaseDatos cargarTodo() throws Exception {
        BaseDatos db = new BaseDatos();
        db.setVideojuegos(videojuegos());
        db.setUsuarios(usuarios());
        db.setPrestamos(prestamos());
        return db;
    }

    /**
     * Funcion para guardar los datos al momento de ser creados o introducidos en la aplicacion
     * @param db la base de datos donde se guardan
     * @throws IOException en caso de que haya problemas a la hora de ejecutar el guardado
     */
    public void guardarTodo(BaseDatos db) throws IOException {
        fVideojuegos.escribir(db.getVideojuegos());
        fUsuarios.escribir(db.getUsuarios());
        fPrestamos.escribir(db.getPrestamos());
    }
}
