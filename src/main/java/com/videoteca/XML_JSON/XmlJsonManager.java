package com.videoteca.XML_JSON;

import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.videoteca.modelo.BaseDatos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlJsonManager {

    /**
     * Funcion para la eneracion del XML
     * @param db la base de datos donde se guarda
     * @param ruta en donde se guarda en la base da datos
     * @throws IOException exepcion que se dispara si hay un error de escritura
     */
    public static void exportarXml(BaseDatos db, String ruta) throws IOException {
        XStream xstream = configurarXStream();
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            xstream.toXML(db, fos);
        }
    }

    /**
     * Funcion para la lectura del XML
     * @param ruta desde donde se lee el XML
     * @return
     * @throws IOException exepcion que se dispara si hay un error de escritura
     */
    public static BaseDatos importarXml(String ruta) throws IOException {
        XStream xstream = configurarXStream();
        try (FileInputStream fis = new FileInputStream(ruta)) {
            return (BaseDatos) xstream.fromXML(fis);
        }
    }

    /**
     * Funcion que genera el JSON
     * @param db la base de datos
     * @param ruta donde colocarlo en la base de datos
     * @throws IOException exepcion que se dispara si hay un error de escritura
     */
    public static void exportarJson(BaseDatos db, String ruta) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(db);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(ruta), StandardCharsets.UTF_8)) {
            writer.write(json);
        }
    }

    /**
     * Configuracion del Xstream con la base de datos
     * @return devuelve el Xstream
     */
    private static XStream configurarXStream() {
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("Videoteca", BaseDatos.class);
        xstream.alias("Videojuego", com.videoteca.modelo.Videojuego.class);
        xstream.alias("Usuario", com.videoteca.modelo.Usuario.class);
        xstream.alias("Prestamo", com.videoteca.modelo.Prestamo.class);
        xstream.addImplicitCollection(BaseDatos.class, "videojuegos");
        xstream.addImplicitCollection(BaseDatos.class, "usuarios");
        xstream.addImplicitCollection(BaseDatos.class, "prestamos");
        return xstream;
    }
}
