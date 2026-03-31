package campeonato_carreras;

import java.util.Random;

/**
 * Representa un piloto participante en el campeonato.
 *
 * <p>Contiene identidad del piloto, coche asignado y puntuacion acumulada.</p>
 */
public class Piloto {
    private String nombre;
    private int dorsal;
    private Coche coche;
    private int puntosAcumulados;


    /**
     * Crea un piloto con su nombre y dorsal.
     *
     * @param nombre nombre del piloto
     * @param dorsal dorsal unico del piloto
     */
    public Piloto(String nombre, int dorsal) {
        this.nombre = nombre;
        this.dorsal = dorsal;
    }

    /**
     * Obtiene el nombre del piloto.
     *
     * @return nombre del piloto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el coche asociado al piloto.
     *
     * @return coche asignado al piloto, o {@code null} si no tiene
     */
    public Coche getCoche() {
        return coche;
    }

    /**
     * Obtiene la puntuacion acumulada del piloto.
     *
     * @return puntos acumulados en el campeonato
     */
    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    /**
     * Define directamente la puntuacion acumulada.
     *
     * @param puntosAcumulados nueva puntuacion total
     */
    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    /**
     * Obtiene el dorsal identificador del piloto.
     *
     * @return dorsal del piloto
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * Asigna un coche al piloto.
     *
     * @param coche coche a asignar
     */
    public void asignarCoche(Coche coche) {
        this.coche = coche;
        System.out.println("El coche con matricula " + coche.getMatricula() + " ha sido asignado correctamente.");
    }

    /**
     * Suma puntos al total acumulado del piloto.
     *
     * @param puntosAcumulados puntos a sumar
     */
    public void sumarPuntos(int puntosAcumulados) {
        if (puntosAcumulados < 0) {
            System.err.println("Los puntos no pueden ser negativos.");
        }
        this.puntosAcumulados += puntosAcumulados;
    }

    /**
     * Muestra por consola la informacion principal del piloto.
     */
    public void mostrarInformacion() {
        System.out.println("=== INFO PILOTO ===");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Dorsal: #" + this.dorsal);
        System.out.println("Puntos en el Campeonato: " + this.puntosAcumulados);

        // Verificamos si tiene coche asignado para evitar errores
        if (this.coche != null) {
            // Accedemos a sus atributos mediante getters
            System.out.println("Vehículo asignado: " + coche.getModelo() + " (Matrícula: " + coche.getMatricula() + ")");
            System.out.println("Velocidad Base: " + coche.getVelocidadBase() + " km/h");
        } else {
            System.out.println("Vehículo asignado: NINGUNO (No puede competir)");
        }
        System.out.println("-------------------");

    }


}
