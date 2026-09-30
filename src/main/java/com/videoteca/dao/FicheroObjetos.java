package com.videoteca.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroObjetos<T extends Serializable> {
    private final File fichero;

    public FicheroObjetos(String ruta) {
        fichero = new File(ruta);
    }

    public List<T> leer() throws IOException, ClassNotFoundException {
        List<T> lista = new ArrayList<>();
        if (!fichero.exists() || fichero.length() == 0) return lista;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            while (true) {
                try {
                    Object objeto = ois.readObject();
                    if (objeto == null) {
                        break;
                    }
                    T dato = (T) objeto;
                    lista.add(dato);
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return lista;
    }

    public void escribir(List<T> lista) throws IOException {
        File padre = fichero.getParentFile();
        if (padre != null) {
            padre.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
            for (T dato : lista) oos.writeObject(dato);
            oos.writeObject(null);
        }
    }
}
