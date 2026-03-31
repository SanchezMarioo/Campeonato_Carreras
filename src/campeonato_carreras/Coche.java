package campeonato_carreras;

import java.util.Random;

/**
 * Modela un coche de competicion con estado de combustible, desgaste y avance.
 */
public class Coche {
    private String matricula;
    private String modelo;
    private double velocidadBase;
    private double combustible;
    private double consumoBase;
    private double distanciaRecorrida;
    private boolean motorExplotado;
    private Random random;
    private boolean asignado; // Por defecto es false


    /**
     * Crea un coche con sus atributos base para competir.
     *
     * @param matricula          identificador del coche
     * @param modelo             nombre del modelo
     * @param velocidadBase      velocidad base de referencia
     * @param combustible        combustible inicial disponible
     * @param consumoBase        consumo base por kilometro
     * @param distanciaRecorrida distancia acumulada inicial
     */
    public Coche(String matricula, String modelo, double velocidadBase, double combustible, double consumoBase, double distanciaRecorrida) {
        random = new Random();
        this.matricula = matricula;
        this.modelo = modelo;
        this.velocidadBase = velocidadBase;
        this.combustible = combustible;
        this.consumoBase = consumoBase;
        this.distanciaRecorrida = distanciaRecorrida;
    }

    /**
     * @return matricula del coche
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @return modelo del coche
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @return velocidad base del coche
     */
    public double getVelocidadBase() {
        return velocidadBase;
    }

    /**
     * @return combustible disponible actualmente
     */
    public double getCombustible() {
        return combustible;
    }

    /**
     * @return consumo base del coche
     */
    public double getConsumoBase() {
        return consumoBase;
    }

    /**
     * @return distancia total acumulada recorrida
     */
    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    /**
     * @return {@code true} si el coche ya esta asignado a un piloto
     */
    public boolean isAsignado() {
        return asignado;
    }

    /**
     * Cambia el estado de asignacion del coche.
     *
     * @param asignado nuevo estado de asignacion
     */
    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    /**
     * Este metodo saca un numero double entre un maximo y un minimo
     *
     * @param min
     * @param max
     * @return devuelve el numero random
     */
    private double calcularRandomDouble(double min, double max) {
        return random.nextDouble(min, max);
    }

    private int calcularVelocidadCarrera() {
        return (int) (velocidadBase * (calcularRandomDouble(0.5, 1.2)));
    }

    /**
     * Avanza el coche durante un turno de carrera.
     * 
     * Calcula velocidad efectiva, consumo y riesgo de rotura del motor para
     * devolver el tiempo empleado en el tramo.
     *
     * @param distanciaTurno distancia del turno en metros
     * @return tiempo del turno, o {0} si el coche no puede continuar
     */
    public double avanzar(double distanciaTurno) {
        if (!puedeContinuar()) return 0;
        int velocidadCarrera = calcularVelocidadCarrera();
        // Si la velocidad es mayor que la de la carrera 
        if (velocidadCarrera > velocidadBase) {
            if (calcularRandomDouble(0, 1) < 0.1) {
                System.out.println("¡BOOM! El motor de " + this.modelo + " ha explotado.");
                this.motorExplotado = true;
                return 0;
            }
        }
        double potenciadorConsumo = velocidadCarrera / velocidadBase;
        double distanciaKm = distanciaTurno / 1000.0;
        double consumoTurno = distanciaKm * (this.consumoBase * potenciadorConsumo);
        if (combustible >= potenciadorConsumo) {
            combustible -= consumoTurno;
            distanciaRecorrida += distanciaTurno;
        } else {
            System.out.println("El coche no puede seguir compitiendo.");
            combustible = 0;
            return 0;
        }
        return distanciaTurno / velocidadCarrera;
    }

    private boolean puedeContinuar() {
        if (combustible >= 0 && !motorExplotado) {
            return true;
        } else {
            return false;
        }
    }


}
