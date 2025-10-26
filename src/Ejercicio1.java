import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que representa una película con su título, director y año de estreno.
 */
class Pelicula
{
    private String titulo;
    private String director;
    private int estreno;

    public Pelicula(String titulo, String director, int estreno)
    {
        this.titulo = titulo;
        this.director = director;
        this.estreno = estreno;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getDirector()
    {
        return director;
    }

    public int getEstreno()
    {
        return estreno;
    }

    @Override
    public String toString()
    {
        return titulo + " (" + director + ", " + estreno + ")";
    }
}

/**
 * Clase principal del ejercicio para prácticar el uso de<b>Streams</b> sobre una lista de películas.
 */
public class Ejercicio1
{
    /**
     * Método principal que crea una lista de películas y aplica distintas
     * operaciones de filtrado, mapeo, ordenación y agregación usando streams.
     */
    public static void main(String[] args)
    {
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(new Pelicula("E.T.", "Steven Spielberg", 1982));
        peliculas.add(new Pelicula("Avatar", "James Cameron", 2009));
        peliculas.add(new Pelicula("Salvar al soldado Ryan", "Steven Spielberg", 1998));
        peliculas.add(new Pelicula("Origen", "Christopher Nolan", 2010));
        peliculas.add(new Pelicula("L.A. Confidential", "Curtis Hanson", 1997));

        //1. Obtener un sublistado con las películas estrenadas en el siglo XXI (a partir del año 2000, inclusive)
        List<Pelicula> peliculasXXI = peliculas.stream()
                .filter(pelicula -> pelicula.getEstreno() >= 2000)
                .toList();
        System.out.println("PELÍCULAS DEL SIGLO XXI");
        peliculasXXI.forEach(System.out::println);

        //2. Mostrar por pantalla, separados por comas, los títulos de las películas de Steven Spielberg
        String titulosSpielberg = peliculas.stream()
                .filter(pelicula -> pelicula.getDirector().equals("Steven Spielberg"))
                .map(Pelicula::getTitulo)
                .collect(Collectors.joining(", "));

        System.out.println("\nTÍTULOS DE STEVEN SPIELBERG");
        System.out.println(titulosSpielberg);

        //3. Obtener un sublistado con los nombres de las películas, ordenados alfabéticamente
        List<String> nombresPelis = peliculas.stream()
                .map(Pelicula::getTitulo)
                .sorted()
                .toList();

        System.out.println("\nNOMBRES DE TODAS LAS PELÍCULAS ORDENADAS");
        nombresPelis.forEach(System.out::println);

        //4. Mostrar por pantalla el año de estreno más reciente
        Integer anyEstrenoReciente = peliculas.stream()
                .mapToInt(Pelicula::getEstreno)
                .max()
                .getAsInt();

        System.out.println("\nAÑO DE ESTRENO MÁS RECIENTE");
        System.out.println(anyEstrenoReciente);

    }
}



