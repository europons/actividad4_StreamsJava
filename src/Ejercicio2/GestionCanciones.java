package Ejercicio2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase que gestiona una colección de canciones para la aplicación <b>Cancioteca</b>.
 * <p>
 * Permite leer y escribir canciones en ficheros, calcular estadísticas,
 * generar rankings, filtrar resultados y agregar nuevas canciones.
 * </p>
 */
public class GestionCanciones {

    /**
     * Lee las canciones desde un archivo de texto y las convierte en objetos {@link Cancion}.
     * <p>Formato esperado: <code>titulo:artista:duracionSeg:reproducciones</code></p>
     *
     * @param nombreArchivo nombre del archivo que contiene las canciones
     * @return lista de canciones leídas; lista vacía si ocurre un error
     */
    public List<Cancion> leerCanciones(String nombreArchivo){

        try (Stream<String> lineas = Files.lines(Paths.get(nombreArchivo))){
            List<Cancion> listaCanciones = lineas
                    .map(linea -> new Cancion(linea.split(":")[0], linea.split(":")[1], Integer.parseInt(linea.split(":")[2]), Long.parseLong(linea.split(":")[3])))
                    .toList();

            System.out.println("Canciones leídas correctamente");

            return listaCanciones;
        }catch (Exception e){
            System.out.println("Error al leer el archivo: "+ e.getMessage());
            return List.of();
        }
    }

    /**
     * Filtra las canciones cuya duración total reproducida supera los 10 minutos(duración * número de reproducciones)
     *
     * @param listaCanciones lista de canciones
     */
    public void filtrarCanciones (List<Cancion> listaCanciones){
        List<Cancion> cancionesMas10 = listaCanciones.stream()
                .filter(cancion -> cancion.getDuracionSeg() * cancion.getReproducciones() > 600)
                .toList();
        try{
            Files.write(Paths.get("ranking.txt"), cancionesMas10.stream().map(Cancion::aFichero).collect(Collectors.toList()));
        }catch (Exception e){
            System.out.println("Error al guardar Top Canciones con mas de 10 min de reproducción: "+ e.getMessage());
        }

        System.out.println("\n CANCIONES CON MÁS DE 10 MINUTOS DE REPRODUCCIONES");
        System.out.println("ºººººººººººººººººººººººººººººººººººººººººººººººººººº");
        cancionesMas10.forEach(System.out::println);

    }

    /**
     * Calcula la duración media de las canciones.
     *
     * @param listaCanciones lista de canciones
     * @return duración media en segundos
     */
    public Double duracionMedia(List<Cancion> listaCanciones){
        return listaCanciones.stream()
                .mapToDouble(Cancion::getDuracionSeg)
                .average()
                .orElse(0.0);
    }

    /**
     * Obtiene el título de la canción con más reproducciones.
     *
     * @param listaCanciones lista de canciones
     * @return título de la canción más reproducida o “No disponible” si la lista está vacía
     */
    public String cancionMasReproducida(List<Cancion> listaCanciones){
        return listaCanciones.stream()
                .max(Comparator.comparing(Cancion::getReproducciones))
                .map(Cancion::getTitulo)
                .orElse("No disponible");
    }

    /**
     * Obtiene el título de la canción con menos reproducciones.
     *
     * @param listaCanciones lista de canciones
     * @return título de la canción menos reproducida o “No disponible” si la lista está vacía
     */
    public String cancionMenosReproducida(List<Cancion> listaCanciones){
        return listaCanciones.stream()
                .min(Comparator.comparing(Cancion::getReproducciones))
                .map(Cancion::getTitulo)
                .orElse("No disponible");
    }

    /**
     * Genera un ranking TOP 3 de las canciones más reproducidas.
     *
     * @param listaCanciones lista de canciones
     * @return cadena con los títulos de las tres canciones más reproducidas, separados por saltos de línea
     */
    public String rankingTOP3 (List<Cancion> listaCanciones) {
        String top3 = listaCanciones.stream()
                .sorted(Comparator.comparing(Cancion::getReproducciones).reversed())
                .map(Cancion::getTitulo)
                .limit(3)
                .collect(Collectors.joining("\n"));

        guardarDatos(duracionMedia(listaCanciones), cancionMasReproducida(listaCanciones), cancionMenosReproducida(listaCanciones), top3);

        return top3;
    }

    /**
     * Agrega una nueva canción a la colección, solicitando los datos al usuario por consola.
     *
     * @param entrada objeto Scanner para leer la entrada del usuario
     */
    public void agregarCancion (Scanner entrada){
        String titulo, artista;
        int duracionSeg;
        long reproducciones;

        System.out.println("\nIntroduce los datos de la nueva canción");
        System.out.println("ªªªªªªªªªªºººººººººººººººººººººººººººººº");
        System.out.print("Título: ");
        titulo = entrada.nextLine();
        System.out.print("Artista: ");
        artista = entrada.nextLine();
        duracionSeg = validarEntero("Duración (en segundos): ", entrada);
        reproducciones = validarLong("Reproducciones: ", entrada);

        List<Cancion> listaActualizada = new ArrayList<>(leerCanciones("canciones.txt"));
        listaActualizada.add(new Cancion(titulo, artista, duracionSeg, reproducciones));

        try{
            Files.write(Paths.get("canciones.txt"), listaActualizada.stream().map(Cancion::aFichero).collect(Collectors.toList()));
            System.out.println("✅ Canción agregada correctamente ✅");
        }catch (Exception e){
            System.out.println("Error al guardar los datos: "+ e.getMessage());
        }
    }

    /**
     * Guarda las estadísticas y el ranking TOP 3 en un archivo de texto (ranking.txt).
     *
     * @param duracionMedia duración media de las canciones
     * @param cancionMasRepro título de la canción más reproducida
     * @param cancionMenosRepro título de la canción menos reproducida
     * @param rankingTop3 cadena con el ranking TOP 3 de canciones
     */
    public void guardarDatos(Double duracionMedia, String cancionMasRepro, String cancionMenosRepro, String rankingTop3){
        List<String> lineasAGuardar = new ArrayList<>();
        lineasAGuardar.add("Duración media: "+duracionMedia+" segundos");
        lineasAGuardar.add("Canción más reproducida: " + cancionMasRepro);
        lineasAGuardar.add("Canción menos reproducida: " + cancionMenosRepro);
        lineasAGuardar.add("Ranking TOP 3:\n" + rankingTop3);

        try{
            Files.write(Paths.get("ranking.txt"), lineasAGuardar);
        }catch (Exception e){
            System.out.println("Error al guardar el ranking y las estadísticas: "+ e.getMessage());
        }
    }

    /**
     * Valida la entrada del usuario para obtener un número entero.
     *
     * @param mensaje mensaje mostrado al usuario
     * @param entrada escáner de entrada
     * @return número entero válido
     */
    private int validarEntero(String mensaje, Scanner entrada){
        int numero=0;
        boolean esValido=false;

        while (!esValido){
            System.out.print(mensaje);
            String entero = entrada.nextLine();
            try{
                numero = Integer.parseInt(entero);
                esValido=true;
            }catch (Exception e){
                System.out.print("⚠️ Entrada no válida. Introduce un número entero: \n");
            }
        }
        return numero;
    }

    /**
     * Valida la entrada del usuario para obtener un número largo.
     *
     * @param mensaje mensaje mostrado al usuario
     * @param entrada escáner de entrada
     * @return número largo válido
     */
    private long validarLong(String mensaje, Scanner entrada){
        long numero=0;
        boolean esValido=false;

        while (!esValido){
            System.out.print(mensaje);
            String entero = entrada.nextLine();
            try{
                numero = Long.parseLong(entero);
                esValido=true;
            }catch (Exception e){
                System.out.print("⚠️ Entrada no válida. Introduce un número entero: \n");
            }
        }
        return numero;
    }
}
