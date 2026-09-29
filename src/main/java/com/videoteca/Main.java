package com.videoteca;

import com.videoteca.model.*;
import com.videoteca.service.VideotecaService;
import com.videoteca.util.XmlJsonManager;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final VideotecaService service = new VideotecaService();

    public static void main(String[] args) {
        new File("data").mkdirs();
        int opcion;
        do {
            mostrarMenu();
            opcion = entero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> listarVideojuegos();
                    case 2 -> altaVideojuego();
                    case 3 -> modificarVideojuego();
                    case 4 -> bajaVideojuego();
                    case 5 -> listarUsuarios();
                    case 6 -> altaUsuario();
                    case 7 -> modificarUsuario();
                    case 8 -> bajaUsuario();
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
        System.out.println("13. EXTRA 1 - Leer XML");
        System.out.println("14. EXTRA 2 - Generar JSON");
        System.out.println("0. Salir");
    }

    private static void listarVideojuegos() throws Exception {
        service.videojuegos().forEach(System.out::println);
    }

    private static void listarUsuarios() throws Exception {
        service.usuarios().forEach(System.out::println);
    }

    private static void listarPrestamos() throws Exception {
        service.prestamos().forEach(System.out::println);
    }

    private static void altaVideojuego() throws Exception {
        int id = entero("ID: ");
        String titulo = texto("Título: ");
        String plataforma = texto("Plataforma: ");
        int anio = entero("Año: ");
        double precio = decimal("Precio: ");
        service.altaVideojuego(new Videojuego(id, titulo, plataforma, anio, precio));
        System.out.println("Alta realizada.");
    }

    private static void modificarVideojuego() throws Exception {
        int id = entero("ID a modificar: ");
        String titulo = texto("Nuevo título: ");
        String plataforma = texto("Nueva plataforma: ");
        int anio = entero("Nuevo año: ");
        double precio = decimal("Nuevo precio: ");
        service.modificarVideojuego(new Videojuego(id, titulo, plataforma, anio, precio));
        System.out.println("Modificación realizada.");
    }

    private static void bajaVideojuego() throws Exception {
        service.bajaVideojuego(entero("ID a dar de baja: "));
        System.out.println("Baja realizada.");
    }

    private static void altaUsuario() throws Exception {
        int id = entero("ID: ");
        String nombre = texto("Nombre: ");
        String email = texto("Email: ");
        service.altaUsuario(new Usuario(id, nombre, email));
        System.out.println("Alta realizada.");
    }

    private static void modificarUsuario() throws Exception {
        int id = entero("ID a modificar: ");
        String nombre = texto("Nuevo nombre: ");
        String email = texto("Nuevo email: ");
        service.modificarUsuario(new Usuario(id, nombre, email));
        System.out.println("Modificación realizada.");
    }

    private static void bajaUsuario() throws Exception {
        service.bajaUsuario(entero("ID a dar de baja: "));
        System.out.println("Baja realizada.");
    }

    private static void crearPrestamo() throws Exception {
        int id = entero("ID préstamo: ");
        int usuario = entero("ID usuario: ");
        int videojuego = entero("ID videojuego: ");
        service.altaPrestamo(new Prestamo(id, usuario, videojuego));
        System.out.println("Préstamo creado.");
    }

    private static void devolverPrestamo() throws Exception {
        service.devolverPrestamo(entero("ID préstamo: "));
        System.out.println("Préstamo devuelto.");
    }

    private static void exportarXml() throws Exception {
        XmlJsonManager.exportarXml(service.cargarTodo(), "data/videoteca.xml");
        System.out.println("XML generado en data/videoteca.xml");
    }

    private static void importarXml() throws Exception {
        BaseDatos db = XmlJsonManager.importarXml("data/videoteca.xml");
        service.guardarTodo(db);
        System.out.println("XML leído correctamente y datos cargados en los tres ficheros.");
    }

    private static void exportarJson() throws Exception {
        XmlJsonManager.exportarJson(service.cargarTodo(), "data/videoteca.json");
        System.out.println("JSON generado en data/videoteca.json");
    }

    private static String texto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int entero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Introduce un entero válido.");
            }
        }
    }

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
