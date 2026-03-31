package campeonato_carreras;

import java.util.Scanner;

/**
 * Gestiona la interaccion por consola del campeonato.
 *
 * <p>Se encarga de leer entradas, mostrar el menu y delegar operaciones al
 * objeto {@link Campeonato}.</p>
 */
public class GestorCarreras {
    private Scanner sc;
    Campeonato campeonato;

    /**
     * Crea el gestor e inicializa scanner y campeonato.
     */
    public GestorCarreras() {
        sc = new Scanner(System.in);
        campeonato = new Campeonato();
    }

    /**
     * Inicia el bucle principal del menu interactivo.
     */
    public void init() {
        cargarDatosPrueba();
        int opcion = 0;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige la opcion: ");
            switch (opcion) {
                case 1 -> crearPiloto();
                case 2 -> crearCoche();
                case 3 -> asignarCocheAPiloto();
                case 4 -> crearCarrera();
                case 5 -> inscribirEnCarrera();
                case 6 -> disputarCarrera();
                case 7 -> campeonato.mostrarRanking();
                case 8 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 8);
    }

    private void mostrarMenu() {
        System.out.println("\n--- 🏁 " + "CAMPEONATO CARRERAS" + " 🏁 ---");
        System.out.println("1. Crear Piloto");
        System.out.println("2. Crear Coche");
        System.out.println("3. Asignar Coche a Piloto");
        System.out.println("4. Crear Carrera");
        System.out.println("5. Inscribir Piloto en Carrera");
        System.out.println("6. 🏎️ DISPUTAR CARRERA 🏎️");
        System.out.println("7. Ver Ranking del Campeonato");
        System.out.println("8. Salir");
    }

    private int leerEntero(String msj) {
        System.out.print(msj);
        while (!sc.hasNextInt()) {
            System.out.println("Introduce un numero.");
            sc.nextLine(); // Limpiar buffer invalido
        }
        int n = sc.nextInt();
        sc.nextLine(); // Consumir el salto de linea pendiente
        return n;
    }

    private String leerString(String msj) {
        System.out.print(msj);
        return sc.nextLine().trim();
    }

    private double leerDouble(String msj) {
        System.out.print(msj);
        while (!sc.hasNextDouble()) {
            System.out.println("Introduce un numero.");
            sc.nextLine(); // Limpiar buffer invalido
        }
        double n = sc.nextDouble();
        sc.nextLine(); // Consumir el salto de linea pendiente
        return n;
    }


    private void disputarCarrera() {
        if (!campeonato.hayCarrerasDisponibles()) {
            System.out.println("No hay carreras creadas aun.");
            return;
        }
        campeonato.mostrarCarreras();
        String nombreCarrera = leerString("Dime el nombre de la carrera a disputar: ");
        Carrera carrera = campeonato.buscarCarreraPorNombre(nombreCarrera);
        if (carrera == null) {
            System.out.println("La carrera no existe.");
        } else {
            Piloto ganador = campeonato.disputarCarrera(carrera);
            if (ganador != null) {
                System.out.println("El ganador de esta carrera es " + ganador.getNombre() + " con el dorsal numero " + ganador.getDorsal());
            }
        }
    }

    private void crearCarrera() {
        String nombre = leerString("Nombre del Gran Premio: ");
        double dist = leerDouble("Distancia total (metros): ");
        Carrera c = new Carrera(nombre, dist);
        campeonato.agregarCarrera(c);
    }

    private void asignarCocheAPiloto() {
        if (!campeonato.hayCochesDisponibles()) {
            System.out.println("No hay coches inscritos aun.");
            return;
        }

        if (!campeonato.hayCochesSinAsignar()) {
            System.out.println("No hay coches sin asignar disponibles.");
            return;
        }

        if (!campeonato.hayPilotosDisponibles()) {
            System.out.println("No hay jugadores inscritos aun.");
            return;
        }
        campeonato.mostrarPilotosDisponibles();
        int dorsal = leerEntero("Dime el dorsal del piloto: ");
        Piloto piloto = campeonato.buscarPilotoPorDorsal(dorsal);
        if (piloto == null) {
            System.out.println("No existe piloto con ese dorsal.");
            return;
        }
        if (piloto.getCoche() != null) {
            System.out.println("Ese piloto ya tiene coche asignado.");
            return;
        }
        campeonato.mostrarCoches();
        String matricula = leerString("Dime la matricula del coche: ");
        Coche coche = campeonato.buscarCochePorMatricula(matricula);
        if (coche == null) {
            System.out.println("No existe coche con esa matricula.");
            return;
        }
        if (coche.isAsignado()) {
            System.out.println("Ese coche ya esta asignado.");
            return;
        }

        campeonato.asignarCochePiloto(piloto, coche);
        System.out.println("El piloto " + piloto.getNombre() + " ha sido asignado a " + coche.getMatricula());
    }


    private void crearPiloto() {
        String nombre = leerString("Nombre del piloto: ");
        int dorsal = leerEntero("Dorsal: ");

        Piloto p = new Piloto(nombre, dorsal);

        campeonato.agregarPiloto(p);
        System.out.println("Piloto registrado.");
    }

    private void crearCoche() {
        String mat = leerString("Matricula: ");

        String mod = leerString("Modelo: ");
        double vel = leerDouble("Velocidad Base: ");
        double comb = leerDouble("Combustible inicial (L): ");
        double cons = leerDouble("Consumo Base (L/100km): ");

        Coche c = new Coche(mat, mod, vel, comb, cons, 0);
        campeonato.agregarCoche(c);
        System.out.println("Coche registrado.");
    }

    private void inscribirEnCarrera() {
        campeonato.mostrarPilotos();
        int dorsal = leerEntero("Dime el dorsal del piloto a inscribir: ");
        Piloto piloto = campeonato.buscarPilotoPorDorsal(dorsal);
        if (piloto == null) {
            System.out.println("El piloto no existe.");
        } else {
            if (piloto.getCoche() != null) {
                campeonato.mostrarCarreras();
                String nombre = leerString("Dime el nombre de la carrera: ");
                Carrera carrera = campeonato.buscarCarreraPorNombre(nombre);
                carrera.inscribirPiloto(piloto);
                System.out.println("El piloto " + piloto.getNombre() + " ha sido inscrito correctamente en la carrera de " + carrera.getNombre());
            } else {
                System.out.println("El piloto no tiene asignado un coche");
            }
        }

    }


    /**
     * Carga un conjunto minimo de datos de ejemplo para pruebas manuales.
     */
    public void cargarDatosPrueba() {
        Piloto p1 = new Piloto("Fernando", 14);
        Piloto p2 = new Piloto("Carlos", 55);
        Piloto p3 = new Piloto("Lewis", 44);

        Coche c1 = new Coche("1234-ABC", "Aston Martin", 320, 80, 6.5, 0);
        Coche c2 = new Coche("5678-DEF", "Ferrari", 330, 78, 6.2, 0);
        Coche c3 = new Coche("9012-GHI", "Mercedes", 325, 79, 6.4, 0);

        Carrera gpMonaco = new Carrera("Monaco", 305000);
        Carrera gpMonza = new Carrera("Monza", 306000);

        campeonato.agregarPiloto(p1);
        campeonato.agregarPiloto(p2);
        campeonato.agregarPiloto(p3);

        campeonato.agregarCoche(c1);
        campeonato.agregarCoche(c2);
        campeonato.agregarCoche(c3);

        campeonato.agregarCarrera(gpMonaco);
        campeonato.agregarCarrera(gpMonza);

        // Dejamos un piloto sin coche para probar también ese flujo.
        campeonato.asignarCochePiloto(p1, c1);
        campeonato.asignarCochePiloto(p2, c2);
        campeonato.asignarCochePiloto(p3, c3);
    }
}
