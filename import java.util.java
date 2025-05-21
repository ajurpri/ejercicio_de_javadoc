import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase que representa a un jugador de fútbol con atributos personales
 * y funcionalidad asociada para ser gestionado dentro de una plantilla.
 * <p>
 * Incluye métodos estáticos para:
 * <ul>
 *     <li>Dar de alta un nuevo jugador</li>
 *     <li>Eliminar un jugador por dorsal</li>
 *     <li>Mostrar la plantilla completa</li>
 *     <li>Filtrar por posición (pendiente de implementación)</li>
 * </ul>
 * </p>
 *
 * @author Antonio David, Alvaro Contreras
 * @version 1.0
 * @since 2025-05-20
 */
public class Jugador {

    /** DNI del jugador (identificador único). */
    String dni;

    /** Nombre completo del jugador. */
    String nombre;

    /** Posición que ocupa el jugador en el campo. */
    public puesto posicion;

    /** Estatura del jugador en metros. */
    double estatura;

    /**
     * Enumeración que define los distintos puestos en los que puede jugar un jugador.
     */
    public enum puesto {
        PORTERO,
        DEFENSA,
        CENTROCAMPISTA,
        DELANTERO
    }

    /**
     * Constructor para crear una nueva instancia de Jugador.
     *
     * @param dni      Documento Nacional de Identidad del jugador.
     * @param nombre   Nombre completo del jugador.
     * @param posicion Posición en la que juega.
     * @param estatura Altura del jugador en metros.
     */
    public Jugador(String dni, String nombre, puesto posicion, double estatura) {
        this.dni = dni;
        this.nombre = nombre;
        this.posicion = posicion;
        this.estatura = estatura;
    }

    /**
     * Representación textual del objeto Jugador.
     *
     * @return Cadena de texto con todos los atributos del jugador.
     */
    @Override
    public String toString() {
        return "Jugador{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", posicion=" + posicion +
                ", estatura=" + estatura +
                '}';
    }

    /**
     * Método para añadir un nuevo jugador a la plantilla.
     * <p>
     * Solicita los datos por consola e inserta el jugador en el mapa con la clave del dorsal.
     * </p>
     *
     * @param plantilla Mapa que representa la plantilla de jugadores, con clave dorsal.
     * @param dorsal    Número de dorsal que se asignará al nuevo jugador.
     *
     * @throws IllegalArgumentException si se introduce una opción de puesto no válida (controlada manualmente).
     */
    static void altaJugador(Map<Integer, Jugador> plantilla, int dorsal) {
        Scanner sc = new Scanner(System.in);

        System.out.print("DNI del jugador: ");
        String dni = sc.nextLine();

        System.out.print("Nombre del jugador: ");
        String nombre = sc.nextLine();

        System.out.println("Selecciona el puesto del jugador:");
        puesto[] puestos = puesto.values();
        for (int i = 0; i < puestos.length; i++) {
            System.out.println((i + 1) + ". " + puestos[i]);
        }

        puesto seleccion = null;
        while (seleccion == null) {
            System.out.print("Introduce el número correspondiente al puesto: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer
            if (opcion >= 1 && opcion <= puestos.length) {
                seleccion = puestos[opcion - 1];
            } else {
                System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }

        System.out.print("Estatura del jugador (en metros): ");
        double estatura = sc.nextDouble();
        sc.nextLine(); // limpiar buffer

        Jugador nuevo = new Jugador(dni, nombre, seleccion, estatura);
        plantilla.put(dorsal, nuevo);
        System.out.println("Jugador añadido con éxito.");
        System.out.println("----------------------------------------------------");
    }

    /**
     * Elimina un jugador de la plantilla dado un dorsal introducido por consola.
     * <p>
     * Si el dorsal no existe en la plantilla, solicita uno nuevo hasta que sea válido.
     * <b>Este método contiene un bucle infinito si no se encuentra un dorsal válido.</b>
     * </p>
     *
     * @param plantilla Mapa con la plantilla de jugadores.
     * @param dorsal    Dorsal objetivo para eliminación (actualmente ignorado en lógica interna).
     * @return El objeto {@link Jugador} que fue eliminado.
     *
     * @throws java.util.InputMismatchException si se introduce un tipo de dato no válido.
     *
     * @deprecated Este método necesita refactorización para evitar bucle infinito.
     */
    static Jugador eliminarJugador(Map<Integer, Jugador> plantilla, int dorsal) {
        System.out.println("¿Qué jugador quieres eliminar de la plantilla por dorsal?:");
        Scanner sc = new Scanner(System.in);
        int dors;

        while (true) {
            System.out.println("¿Qué jugador quieres eliminar de la plantilla por dorsal?:");
            dors = sc.nextInt();
            sc.nextLine();

            if (!plantilla.containsKey(dors)) {
                System.out.println("Ese jugador no está, escriba otro:");
            } else {
                System.out.println("El jugador eliminado es: " + plantilla.get(dors));
                Jugador eliminado = plantilla.remove(dors);
                System.out.println("----------------------------------------------------");
                return eliminado;
            }
        }
    }

    /**
     * Muestra la plantilla completa de jugadores con sus dorsales.
     *
     * @param plantilla Mapa que contiene los jugadores actuales.
     */
    static void mostrar(Map<Integer, Jugador> plantilla) {
        System.out.println("La plantilla de jugadores es:");
        for (Map.Entry<Integer, Jugador> entry : plantilla.entrySet()) {
            System.out.println("Dorsal " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("-----------------------------------------------------");
    }

    /**
     * Muestra los jugadores de la plantilla que ocupan una determinada posición.
     *
     * @param plantilla Mapa de jugadores.
     * @param posicion  Posición a filtrar, esperada como cadena (por ejemplo: "PORTERO").
     *
     * @implNote Este método está pendiente de implementación.
     * @see String#equalsIgnoreCase(String)
     *
     * @todo Implementar la lógica de filtrado de jugadores por posición.
     */
    static void mostrarPosicion(Map<Integer, Jugador> plantilla, String posicion) {
        // TODO: Implementar filtrado por puesto (posición)
    }
}