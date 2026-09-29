package com.videoteca.util;

import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.videoteca.model.BaseDatos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlJsonManager {
    public static void exportarXml(BaseDatos db, String ruta) throws IOException {
        XStream xstream = configurarXStream();
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            xstream.toXML(db, fos);
        }
    }

    // EXTRA 1: lectura de XML con XStream.
    public static BaseDatos importarXml(String ruta) throws IOException {
        XStream xstream = configurarXStream();
        try (FileInputStream fis = new FileInputStream(ruta)) {
            return (BaseDatos) xstream.fromXML(fis);
        }
    }

    // EXTRA 2: generación de JSON.
    public static void exportarJson(BaseDatos db, String ruta) throws IOException {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(db);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(ruta), StandardCharsets.UTF_8)) {
            writer.write(json);
        }
    }

    private static XStream configurarXStream() {
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("Videoteca", BaseDatos.class);
        xstream.alias("Videojuego", com.videoteca.model.Videojuego.class);
        xstream.alias("Usuario", com.videoteca.model.Usuario.class);
        xstream.alias("Prestamo", com.videoteca.model.Prestamo.class);
        xstream.addImplicitCollection(BaseDatos.class, "videojuegos");
        xstream.addImplicitCollection(BaseDatos.class, "usuarios");
        xstream.addImplicitCollection(BaseDatos.class, "prestamos");
        return xstream;
    }
}
