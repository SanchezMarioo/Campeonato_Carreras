package campeonato_carreras;

import java.util.ArrayList;

public class Campeonato {
    private ArrayList<Carrera> carreras;
    private ArrayList<Piloto> pilotosTotales;
    private ArrayList<Coche> coches;

    /**
     * Crea un campeonato vacio con listas de pilotos, coches y carreras.
     */
    public Campeonato() {
        carreras = new ArrayList<>();
        pilotosTotales = new ArrayList<>();
        coches = new ArrayList<>();
    }

    /**
     * Registra un coche en el campeonato.
     *
     * @param coche coche a registrar
     */
    public void agregarCoche(Coche coche) {
        coches.add(coche);
        System.out.println("El coche " + coche.getMatricula() + " ha sido añadido correctamente");
    }

    /**
     * Registra un piloto en el campeonato.
     *
     * @param piloto piloto a registrar
     */
    public void agregarPiloto(Piloto piloto) {
        pilotosTotales.add(piloto);
        System.out.println("El piloto " + piloto.getNombre() + " ha sido añadido correctamente.");
    }

    /**
     * Registra una carrera en el calendario del campeonato.
     *
     * @param carrera carrera a registrar
     */
    public void agregarCarrera(Carrera carrera) {
        carreras.add(carrera);
        System.out.println("La carrera " + carrera.getNombre() + " ha sido añadida correctamente");
    }

    /**
     * Indica si existe al menos un coche en el campeonato.
     *
     * @return true si hay coches registrados; false en caso contrario
     */
    public boolean hayCochesDisponibles() {
        return !estaVacio(coches);
    }

    /**
     * Indica si existe al menos un piloto en el campeonato.
     *
     * @return true si hay pilotos registrados; false en caso contrario
     */
    public boolean hayPilotosDisponibles() {
        return !estaVacio(pilotosTotales);
    }

    /**
     * Indica si existe al menos una carrera en el campeonato.
     *
     * @return true si hay carreras registradas;  false en caso contrario
     */
    public boolean hayCarrerasDisponibles() {
        return !estaVacio(carreras);
    }


    /**
     * Muestra por consola los pilotos que todavia no tienen coche asignado.
     */
    public void mostrarPilotosDisponibles() {

        System.out.println("\n=== PILOTOS DISPONIBLES ===");

        // Definimos los encabezados con el mismo ancho que los datos
        // Usamos %-15s para AGRUPAR el texto a la izquierda
        System.out.printf("%-15s | %-6s %n", "NOMBRE", "DORSAL");
        System.out.println("----------------------------------");
        for (Piloto piloto : pilotosTotales) {
            if (piloto.getCoche() == null) {
                System.out.printf("%-15s | %-6d %n",
                        piloto.getNombre(),
                        piloto.getDorsal());
            }
        }
        System.out.println("----------------------------------");
    }

    /**
     * Muestra por consola los pilotos del campeonato que ya tienen coche asignado.
     */
    public void mostrarPilotos() {
        System.out.printf("%-15s | %-6s %n", "NOMBRE", "DORSAL");
        System.out.println("----------------------------------");
        for (Piloto piloto : pilotosTotales) {
            if (piloto.getCoche() != null) {
                System.out.printf("%-15s | %-6d %n",
                        piloto.getNombre(),
                        piloto.getDorsal());
            }
        }
        System.out.println("----------------------------------");
    }

    /**
     * Muestra por consola las carreras registradas.
     */
    public void mostrarCarreras() {
        for (Carrera carrera : carreras) {
            System.out.printf("%-15s | %-6.2f %n",
                    carrera.getNombre(),
                    carrera.getDistancia());
        }
    }

    /**
     * Muestra por consola los coches no asignados.
     *
     * @return {true} si hay coches registrados;  false si la lista esta vacia
     */
    public void mostrarCoches() {

        System.out.printf("%-15s | %-6s %n", "MATRICULA", "MODELO");
        System.out.println("----------------------------------");
        for (Coche coche : coches) {
            if (!coche.isAsignado()) {
                System.out.printf("%-15s | %-15s %n",
                        coche.getMatricula(),
                        coche.getModelo());
            }
        }
        System.out.println("----------------------------------");


    }

    /**
     * Indica si existe al menos un coche libre (no asignado a piloto).
     *
     * @return true si hay algun coche sin asignar; false en caso contrario
     */
    public boolean hayCochesSinAsignar() {
        for (Coche coche : coches) {
            if (!coche.isAsignado()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Asigna un coche a un piloto y marca el coche como asignado.
     *
     * @param piloto piloto que recibira el coche
     * @param coche  coche a asignar
     */
    public void asignarCochePiloto(Piloto piloto, Coche coche) {
        coche.setAsignado(true);
        piloto.asignarCoche(coche);
    }

    /**
     * Disputa una carrera y devuelve su ganador.
     *
     * @param carrera carrera a disputar
     * @return piloto ganador, o  null si no hay inscritos
     */
    public Piloto disputarCarrera(Carrera carrera) {

        Piloto ganador = carrera.inciarCarrera();
        if (ganador == null) {
            System.out.println("No hay pilotos apuntados a esta carrera.");
            return null;
        } else {
            ganador.sumarPuntos(10);
            return ganador;
        }

    }

    /**
     * Ordena el ArrayList de Pilotos en base a los puntos obtenidos
     */
    private void ordenarRanking() {
        for (int i = 0; i < pilotosTotales.size(); i++) {
            for (int j = 0; j < pilotosTotales.size() - i - 1; j++) {
                if (pilotosTotales.get(j).getPuntosAcumulados() < pilotosTotales.get(j + 1).getPuntosAcumulados()) {
                    Piloto temporal = pilotosTotales.get(j);
                    pilotosTotales.set(j, pilotosTotales.get(j + 1));
                    pilotosTotales.set(j + 1, temporal);
                }
            }
        }
    }

    public void mostrarRanking() {
        ordenarRanking();
        System.out.println("======================================================================================");
        System.out.printf("| %-5s | %-15s | %-10s | %-30s | %-10s | %n", "Pos", "Piloto", "Dorsal", "Coche (Matrícula)", "Puntos");
        for (int i = 0; i < pilotosTotales.size(); i++) {
            Piloto piloto = pilotosTotales.get(i);
            Coche coche = piloto.getCoche();
            System.out.printf("| %-5s | %-15s | %-10d | %-30s | %-10d | %n",
                    (i + 1) + "º", piloto.getNombre(), piloto.getDorsal(),
                    coche.getMatricula() + " (" + coche.getModelo() + ")", piloto.getPuntosAcumulados());

        }
        System.out.println("======================================================================================");


    }

    /**
     * Busca un piloto por dorsal.
     *
     * @param dorsal dorsal del piloto a localizar
     * @return piloto encontrado, o  null si no existe
     */
    public Piloto buscarPilotoPorDorsal(int dorsal) {
        if (estaVacio(pilotosTotales)) return null;
        for (Piloto p : pilotosTotales) {
            if (p.getDorsal() == dorsal) {
                return p;
            }
        }
        return null; // No encontrado
    }

    /**
     * Busca una carrera por nombre (ignorando mayusculas y minusculas).
     *
     * @param nombre nombre de la carrera a localizar
     * @return carrera encontrada, o null si no existe
     */
    public Carrera buscarCarreraPorNombre(String nombre) {
        if (estaVacio(carreras)) return null;
        for (Carrera c : carreras) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null; // No encontrado
    }


    /**
     * Busca un coche por matricula (ignorando mayusculas y minusculas).
     *
     * @param matricula matricula del coche a localizar
     * @return coche encontrado, o  null si no existe
     */
    public Coche buscarCochePorMatricula(String matricula) {
        if (estaVacio(coches)) return null;
        for (Coche c : coches) {
            // Usamos equalsIgnoreCase para que no importe mayúsculas/minúsculas
            if (c.getMatricula().equalsIgnoreCase(matricula)) {
                return c;
            }
        }
        return null; // No encontrado
    }

    /**
     * Comprueba si una lista esta vacia.
     *
     * @param items lista a validar
     * @param <T>   tipo de elemento de la lista
     * @return true si la lista no contiene elementos;  false en caso contrario
     */
    public <T> boolean estaVacio(ArrayList<T> items) {
        return items.isEmpty();
    }
}
