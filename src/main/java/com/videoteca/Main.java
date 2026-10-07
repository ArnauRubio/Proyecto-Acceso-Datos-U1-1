package com.videoteca;

import com.videoteca.modelo.*;
import com.videoteca.Acciones.VideotecaAcciones;
import com.videoteca.XML_JSON.XmlJsonManager;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static  Scanner sc = new Scanner(System.in);
    private static  VideotecaAcciones acciones = new VideotecaAcciones();

    public static void main(String[] args) {
        new File("data").mkdirs();
        int opcion;
        do {
            mostrarMenu();
            opcion = entero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> listarVideojuegos();
                    case 2 -> crearVideojuego();
                    case 3 -> modificarVideojuego();
                    case 4 -> borrarVideojuego();
                    case 5 -> listarUsuarios();
                    case 6 -> crearUsuario();
                    case 7 -> modificarUsuario();
                    case 8 -> borrarUsuario();
                    case 9 -> listarPrestamos();
                    case 10 -> crearPrestamo();
                    case 11 -> devolverPrestamo();
                    case 12 -> exportarXml();
                    case 13 -> importarXml();
                    case 14 -> exportarJson();
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== VIDEOTECA UD1 =====");
        System.out.println("1. Listar videojuegos");
        System.out.println("2. Crear videojuego");
        System.out.println("3. Modificar videojuego");
        System.out.println("4. Borrar videojuego");
        System.out.println("5. Listar usuarios");
        System.out.println("6. Crear usuario");
        System.out.println("7. Modificar usuario");
        System.out.println("8. Borrar usuario");
        System.out.println("9. Listar préstamos");
        System.out.println("10. Crear préstamo");
        System.out.println("11. Devolver préstamo");
        System.out.println("12. Exportar XML (XStream)");
        System.out.println("13. Leer XML");
        System.out.println("14. Generar JSON");
        System.out.println("0. Salir");
    }

    /**
     * Funcion para mostrar los videojuegos disponibles
     * @throws Exception si ocurre algun error de lectura
     */
    private static void listarVideojuegos() throws Exception {
        List<Videojuego> videojuegos = acciones.videojuegos();

        for (Videojuego videojuego : videojuegos) {
            System.out.println(videojuego);
        }
    }

    /**
     * Funcion que lista los usuarios existentes
     * @throws Exception en caso de error de lectura
     */
    private static void listarUsuarios() throws Exception {
        List<Usuario> usuarios = acciones.usuarios();

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    /**
     * Funcion que lista los prestamos activos
     * @throws Exception en caso de error de lectura
     */
    private static void listarPrestamos() throws Exception {
        List<Prestamo> prestamos = acciones.prestamos();

        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo);
        }
    }

    /**
     * Funcion que permite la creacion grafica de videojuegos
     * @throws Exception si ocurre un error de lectura o ya existe ese id
     */
    private static void crearVideojuego() throws Exception {
        int id = entero("ID: ");
        String titulo = texto("Título: ");
        String plataforma = texto("Plataforma: ");
        int anio = entero("Año: ");
        double precio = decimal("Precio: ");

        Videojuego videojuego = new Videojuego(
                id,
                titulo,
                plataforma,
                anio,
                precio
        );

        acciones.crearVideojuego(videojuego);

        System.out.println("Creación realizada.");
    }

    /**
     * Funcion que modifica graficamente los juegos
     * @throws Exception en caso de error de lectura
     */
    private static void modificarVideojuego() throws Exception {
        int id = entero("ID a modificar: ");
        String titulo = texto("Nuevo título: ");
        String plataforma = texto("Nueva plataforma: ");
        int anio = entero("Nuevo año: ");
        double precio = decimal("Nuevo precio: ");

        Videojuego videojuego = new Videojuego(
                id,
                titulo,
                plataforma,
                anio,
                precio
        );

        acciones.modificarVideojuego(videojuego);

        System.out.println("Modificación realizada.");
    }

    /**
     * Funcion para el borrado grafico de videojuegos
     * @throws Exception en caso de error de lectura
     */
    private static void borrarVideojuego() throws Exception {
        acciones.borrarVideojuego(entero("ID a borrar: "));
        System.out.println("Videojuego eliminado.");
    }

    /**
     * Funcion para la creacion grafica de ususarios
     * @throws Exception en caso de error de lectura
     */
    private static void crearUsuario() throws Exception {
        int id = entero("ID: ");
        String nombre = texto("Nombre: ");
        String email = texto("Email: ");
        acciones.crearUsuario(new Usuario(id, nombre, email));
        System.out.println("Usurio creado.");
    }

    /**
     * Funcion para la modificacion grafica de usuarios
     * @throws Exception en caso de error de lectura
     */
    private static void modificarUsuario() throws Exception {
        int id = entero("ID a modificar: ");
        String nombre = texto("Nuevo nombre: ");
        String email = texto("Nuevo email: ");
        acciones.modificarUsuario(new Usuario(id, nombre, email));
        System.out.println("Modificación realizada.");
    }

    /**
     * Funcion para el borrado grafico de usuarios
     * @throws Exception en caso de error de lectura
     */
    private static void borrarUsuario() throws Exception {
        acciones.borrarUsuario(entero("ID a borrar: "));
        System.out.println("Usuario eliminado.");
    }

    /**
     * Funcion para la creacion grafica de prestamos
     * @throws Exception en caso de error de lectura
     */
    private static void crearPrestamo() throws Exception {
        int id = entero("ID préstamo: ");
        int usuario = entero("ID usuario: ");
        int videojuego = entero("ID videojuego: ");
        acciones.crearPrestamo(new Prestamo(id, usuario, videojuego));
        System.out.println("Préstamo creado.");
    }

    /**
     * Funcion para devoler graficamente un prestamo
     * @throws Exception en caso de error de lectura
     */
    private static void devolverPrestamo() throws Exception {
        acciones.devolverPrestamo(entero("ID préstamo: "));
        System.out.println("Préstamo devuelto.");
    }

    /**
     * Funcion para exportar XMLs
     * @throws Exception en caso de error de lectura
     */
    private static void exportarXml() throws Exception {
        XmlJsonManager.exportarXml(acciones.cargarTodo(), "data/videoteca.xml");
        System.out.println("XML crado");
    }

    /**
     * Funcion para importar XMLs
     * @throws Exception en caso de error de lectura
     */
    private static void importarXml() throws Exception {
        BaseDatos db = XmlJsonManager.importarXml("data/videoteca.xml");
        acciones.guardarTodo(db);
        System.out.println("XML importado correctamente");
    }

    /**
     * Funcion para exportar JSONs
     * @throws Exception en caso de errror de lectura
     */
    private static void exportarJson() throws Exception {
        XmlJsonManager.exportarJson(acciones.cargarTodo(), "data/videoteca.json");
        System.out.println("JSON creado correctamente");
    }

    /**
     * Elimina los espacios del principio y final de un texto
     * @param mensaje que solicita la entrada de texto
     * @return el texto sin espacios al inicio y final
     */
    private static String texto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    /**
     * Solicita un numero entero correcto o pide un nuevo valor
     * @param mensaje el texto de solicitud
     * @return el entero introducido
     */
    private static int entero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Introduce un entero válido.");
            }
        }
    }

    /**
     * Solicita un numero decimal correcto o piden de nuevo un valor
     * @param mensaje el texto de solicitud
     * @return el decimal introducido
     */
    private static double decimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(texto(mensaje).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }
    }

}
