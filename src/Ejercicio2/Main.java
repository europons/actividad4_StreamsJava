package Ejercicio2;

import java.util.List;
import java.util.Scanner;

/**
 * Clase principal del programa <b>Cancioteca</b>.
 * <p>
 * Permite gestionar una lista de canciones mediante un menú interactivo en consola.
 * Las operaciones disponibles incluyen filtrado, cálculo de duración media, canción más y menos
 * reproducida, ranking top3 de reproducciones y la posibilidad de agregar nuevas canciones.
 * </p>
 */
public class Main {
    /** Scanner para la entrada de datos por consola */
    private static final Scanner entrada = new Scanner(System.in);
    /** Instancia de la clase de gestión de canciones */
    private static final GestionCanciones GESTION_CANCIONES = new GestionCanciones();
    /** Opción del menú para salir del programa */
    private static final String OPCIONES_MENU = "7";

    /**
     * Método principal que carga la lista inicial de canciones desde el archivo
     * y lanza el flujo principal de la aplicación.
     */
    public static void main(String[] args) {
        List<Cancion> listaCanciones = GESTION_CANCIONES.leerCanciones("canciones.txt");
        flujoPrincipal(listaCanciones);
    }


    /**
     * Controla el flujo principal del programa, mostrando el menú,
     * leyendo la opción seleccionada y ejecutando la acción correspondiente.
     *
     * @param listaCanciones lista de canciones cargadas desde el archivo
     */
    private static void flujoPrincipal (List<Cancion> listaCanciones) {
        String opcion;

        do {
            opcion= menuPrincipal();
            switch (opcion){
                case "1":
                    GESTION_CANCIONES.filtrarCanciones(listaCanciones);
                    break;
                case "2":
                    Double media = GESTION_CANCIONES.duracionMedia(listaCanciones);
                    System.out.println("\n🎵 La duración media de las canciones es: " + media + " segundos.");
                    break;
                case "3":
                    String masRepro = GESTION_CANCIONES.cancionMasReproducida(listaCanciones);
                    System.out.println("\n🔥 La canción más reproducida es: " + masRepro);
                    break;
                case "4":
                    String menosRepro = GESTION_CANCIONES.cancionMenosReproducida(listaCanciones);
                    System.out.println("\n📉 La canción menos reproducida es: " + menosRepro);
                    break;
                case "5":
                    String top3 = GESTION_CANCIONES.rankingTOP3(listaCanciones);
                    System.out.println("\n🏆 Ranking TOP 3 de canciones más reproducidas:\n" + top3);
                    break;
                case "6":
                    GESTION_CANCIONES.agregarCancion(entrada);
                    listaCanciones = GESTION_CANCIONES.leerCanciones("canciones.txt");
                    GESTION_CANCIONES.rankingTOP3(listaCanciones);
                    break;
                case "7":
                    System.out.println("👋 ¡Hasta luego!");
                    break;
                default:
                    System.out.println("⚠️ Opción no válida ⚠️");
                    break;
            }
        }while(!opcion.equals(OPCIONES_MENU));
    }


    /**
     * Muestra el menú principal en consola y devuelve la opción elegida por el usuario.
     *
     * @return opción introducida por el usuario
     */
    private static String menuPrincipal() {
        System.out.println("\n");
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("          \uD83C\uDFA7✨   CANCIOTECA   ✨\uD83C\uDFA7      ");
        System.out.println("════════════════════════════════════════════════════");
        System.out.println(" 1️⃣ Filtrar canciones con más de 10 minutos totales");
        System.out.println(" 2️⃣ Calcular duración media");
        System.out.println(" 3️⃣ Mostrar canción más reproducida");
        System.out.println(" 4️⃣ Mostrar canción menos reproducida");
        System.out.println(" 5️⃣ Generar ranking TOP 3");
        System.out.println(" 6️⃣ Agregar nueva canción");
        System.out.println(" 7️⃣  🚪  SALIR");
        System.out.println("════════════════════════════════════════════════════");
        System.out.print("👉 Selecciona una opción: ");
        return entrada.nextLine();
    }
}
