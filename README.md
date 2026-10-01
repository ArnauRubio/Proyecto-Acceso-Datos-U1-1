# Proyecto 1ª Evaluacion Acceso Datos

Aplicación de Java sobre una Videoteca

- He creado 3 ficheros binarios:
    - -data/videojuegos.dat
    - -data/usuarios.dat
    - -data/prestamos.dat
- Y 4 clases serializables:
    - -Videojuego
    - -Usuario
    - -Prestamo
    - -BaseDatos
-
- El programa funciona relacionando ficheros mediante IDs
- Exporta a XML con XStream
- Control de errores y validación de entradas

## Extras

He intentado añadir estos puntos extra:

1. Lectura de ficheros XML
2. Generación de ficheros JSON
3. Control de errores

## Ejecución en IntelliJ

1. Abrir la carpeta del proyecto.
2. Esperar a que Maven descargue XStream y Gson.
3. Ejecutar `com.videoteca.Main`.
4. Los ficheros se generan dentro de `data/`.

## Nota

Los `.dat` se escriben mediante `ObjectOutputStream` y se leen con `ObjectInputStream`.
Al terminar la escritura se guarda `null` como marca de fin de fichero.
