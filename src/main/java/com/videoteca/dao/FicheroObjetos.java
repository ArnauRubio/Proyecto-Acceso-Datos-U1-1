package com.videoteca.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para la administracion de los ficheros de las demas clases
 * @param <T> el dato a tratar
 */
public class FicheroObjetos<T extends Serializable> {
    private File fichero;

    public FicheroObjetos(String ruta) {
        fichero = new File(ruta);
    }

    /**
     * Metodo para leer los ficheros
     * @return una lista con los datos leidos
     * @throws IOException en caso de que no se encuentre una lista donde guardar los datos,
     * o un fichero para leer
     * @throws ClassNotFoundException
     */
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

    /**
     * Metodo para la escritura de los ficheros
     * @param lista desde las cuales se escribe la informacion
     * @throws IOException en caso de que no exista la lista
     */
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
