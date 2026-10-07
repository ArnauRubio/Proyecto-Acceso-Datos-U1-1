package com.videoteca.XML_JSON;

import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.videoteca.modelo.BaseDatos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlJsonManager {

    /**
     * Funcion para exportar la BD a XML
     * @param db datos a exportar
     * @param ruta en donde se guarda el XML
     * @throws IOException exepcion que se dispara si hay un error de escritura
     */
    public static void exportarXml(BaseDatos db, String ruta) throws IOException {
        XStream xstream = configurarXStream();
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            xstream.toXML(db, fos);
        }
    }

    /**
     * Funcion para importar la BD desde el XML
     * @param ruta desde donde se lee el XML
     * @return la BD
     * @throws IOException exepcion que se dispara si hay un error de lectura o apertura
     */
    public static BaseDatos importarXml(String ruta) throws IOException {
        XStream xstream = configurarXStream();
        try (FileInputStream fis = new FileInputStream(ruta)) {
            return (BaseDatos) xstream.fromXML(fis);
        }
    }

    /**
     * Funcion que genera el JSON a partir de la BD
     * @param db datos a exportar
     * @param ruta destino del archivo
     * @throws IOException exepcion que se dispara si hay un error de escritura o apertura
     */
    public static void exportarJson(BaseDatos db, String ruta) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(db);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(ruta), StandardCharsets.UTF_8)) {
            writer.write(json);
        }
    }

    /**
     * Configuracion del Xstream con la aplicacion
     * @return devuelve el Xstream instanciado
     */
    private static XStream configurarXStream() {
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("Videoteca", BaseDatos.class);
        xstream.alias("Videojuego", com.videoteca.modelo.Videojuego.class);
        xstream.alias("Usuario", com.videoteca.modelo.Usuario.class);
        xstream.alias("Prestamo", com.videoteca.modelo.Prestamo.class);
        xstream.addImplicitCollection(BaseDatos.class, "videojuegos",
                "Videojuego", com.videoteca.modelo.Videojuego.class);
        xstream.addImplicitCollection(BaseDatos.class, "usuarios",
                "Usuario", com.videoteca.modelo.Usuario.class);
        xstream.addImplicitCollection(BaseDatos.class, "prestamos",
                "Prestamo", com.videoteca.modelo.Prestamo.class);
        return xstream;
    }
}
