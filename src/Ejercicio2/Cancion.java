package Ejercicio2;

/**
 * Clase que representa una canción dentro de la aplicación <b>Cancioteca</b>.
 * <p>
 * Contiene información sobre el título, artista, duración y número de reproducciones.
 * Incluye métodos para formatear la salida según distintos contextos (archivo, ranking, visualización).
 * </p>
 */
public class Cancion {
    /** Atributos de la canción */
    private final String titulo;
    private final String artista;
    private final int duracionSeg;       // duración en segundos
    private final long reproducciones;   // número de reproducciones

    /**
     * Crea una nueva canción con los datos especificados.
     *
     * @param titulo         título de la canción
     * @param artista        nombre del artista o grupo
     * @param duracionSeg    duración en segundos
     * @param reproducciones número total de reproducciones
     */
    public Cancion(String titulo, String artista, int duracionSeg, long reproducciones) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSeg = duracionSeg;
        this.reproducciones = reproducciones;
    }

    /**Getters */
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracionSeg() { return duracionSeg; }
    public long getReproducciones() { return reproducciones; }

    /** Formato para guardar en fichero */
    public String aFichero() {
        return String.format("%s:%s:%d:%d", titulo, artista, duracionSeg, reproducciones);
    }

    /** Formato para ranking */
    public String formatoRanking() {
        return String.format("%s (%s) - %,d reproducciones", titulo, artista, reproducciones);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%ds, %,d plays]", titulo, artista, duracionSeg, reproducciones);
    }

}
