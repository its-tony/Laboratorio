/*
 * Autor: Antony Portillo
 * Curso: CC2008 
 * Descripcion: Vista por consola. Muestra menu, valida entradas con try/catch
 *              (via ManejadorEntrada) y coordina el flujo con JuegoMemoria
 * Notas:
 *   - Unico uso de Scanner en todo el programa.
 *   - Casillas ocultas se muestran como "." mediante Tablero.construirVista().
 * Fecha de creacion: [06/09/2025]
 * Ultima modificacion: [07/09/2025]
 */

import java.util.Scanner;

public class Principal {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Configurar primera partida
        JuegoMemoria jm = configurarPartida();

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu(jm);
            opcion = leerOpcion();

            if (opcion == 1) {
                // Ver tablero
            System.out.println("\nTablero COMPLETO (no hagas trampa >:( ) : ");
            System.out.println(jm.getTablero().construirVistaCompleta());
            } else if (opcion == 2) {
              
                ejecutarTurno(jm);

                if (jm.juegoTerminado()) {
                    System.out.println("\n¡Partida terminada!");
                    mostrarResultados(jm);

                    if (preguntarNuevaPartida()) {
                        jm = configurarPartida();
                    } else {
                        opcion = 0; // salir sin usar break
                    }
                }

            } else if (opcion == 3) {
                mostrarResultados(jm);
            } else if (opcion == 4) {
                // Nueva partida 
                if (preguntarNuevaPartida()) {
                    jm = configurarPartida();
                }

            } else if (opcion == 0) {
                System.out.println("Shau shauuuu");
            } else {
                System.out.println("Opcion no validaaaaa");
            }
            System.out.println();
        }

        sc.close();
    }

    // menú
    private static void mostrarMenu(JuegoMemoria jm) {
        System.out.println("juego de memoria");
        System.out.println("Turno de: " + jm.getJugadorActual().getNombre());
        System.out.println("1) Ver tablero");
        System.out.println("2) Jugar turno (destapar 2 casillas)");
        System.out.println("3) Ver marcador");
        System.out.println("4) Nueva partida");
        System.out.println("0) Salir");
    }

    private static int leerOpcion() {
        boolean listo = false;
        int op = -1;
        while (!listo) {
            System.out.print("Elige una opcion: ");
            String s = sc.nextLine();
            try {
                op = ManejadorEntrada.parseEnteroEnRango(s, 0, 4);
                listo = true;
            } catch (Exception ex) {
                System.out.println("La entrada es invalida: " + ex.getMessage());
            }
        }
        return op;
    }

    // configuracion

    private static JuegoMemoria configurarPartida() {
        System.out.println("\n Configurar nueva partida ");

        // Dimension del tablero (4 o 6)
        int dim = leerDimension();

        // Nombres de los jugadores
        String nombre1 = leerTexto("Ingresa el el nombre del Jugador 1: ");
        if (nombre1.trim().isEmpty()) nombre1 = "Jugador 1";

        String nombre2 = leerTexto("Ingresa el numero del Jugador 2: ");
        if (nombre2.trim().isEmpty()) nombre2 = "Jugador 2";

        // Crear emojis y matriz barajada
        Emojis em = new Emojis();
        String[][] matriz = em.crearMatrizBarajada(dim, dim);

        // Crear tablero
        Tablero t = new Tablero(dim, dim, matriz);

        // Crear jugadores
        Jugador j1 = new Jugador(nombre1);
        Jugador j2 = new Jugador(nombre2);

        // Crear juego
        JuegoMemoria jm = new JuegoMemoria(t, j1, j2);

        System.out.println("\n¡Partida creada! Tablero " + dim + "x" + dim + " listo.");
        System.out.println(t.construirVista());
        System.out.println();
        return jm;
    }

    private static int leerDimension() {
        boolean listo = false;
        int dim = 4;
        while (!listo) {
            System.out.print("Elige dimension de tablero (4 o 6): ");
            String s = sc.nextLine();
            try {
                dim = ManejadorEntrada.parseDimensionTablero(s);
                listo = true;
            } catch (Exception ex) {
                System.out.println("La entrada es invalida: " + ex.getMessage());
            }
        }
        return dim;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String s = sc.nextLine();
        // Permitimos vacio para los nombres 
        return s;
    }

 //los turnos 

    private static void ejecutarTurno(JuegoMemoria jm) {
        System.out.println("\nTurno de: " + jm.getJugadorActual().getNombre() );
        int[] p1 = pedirCoordenadasValidas(jm, "Primera casilla");

  

        // Pedir la segunda coordenada valida (no debe ser la misma y debe estar oculta)
        int[] p2 = pedirCoordenadasValidas(jm, "Segunda casilla");

        // Intentar jugar
        boolean valida = jm.jugarTurno(p1[0], p1[1], p2[0], p2[1]);

        if (!valida) {
            System.out.println("El movimiento es invalido, revisa que no repitas casilla ni elijas descubiertas.");
            return;
        }

        System.out.println("\nTablero tras la jugada: ");
        System.out.println(jm.getTablero().construirVista());

       if (jm.fueUltimoAcierto()) {
    System.out.println("¡Acierto! " + jm.getJugadorActual().getNombre() + " gana 1 punto y continua su turno yei ");
} else {
    System.out.println("No hubo coincidencia.");
    System.out.print("Presiona Enter para continuar :D ");
    sc.nextLine(); // pausa para que el jugador vea las cartas

    // ahora sí, ocultamos ambas casillas
    jm.getTablero().ocultarCasilla(p1[0], p1[1]);
    jm.getTablero().ocultarCasilla(p2[0], p2[1]);

    // muestra cómo quedó el tablero ya oculto
    System.out.println("\nTablero actualizado: ");
    System.out.println(jm.getTablero().construirVista());

    System.out.println("Cambio de turno ");
    jm.cambiarTurno();
}

    }
    private static int[] pedirCoordenadasValidas(JuegoMemoria jm, String etiqueta) {
        boolean listo = false;
        int fila = -1, col = -1;
        int filas = jm.getTablero().getFilas();
        int cols  = jm.getTablero().getColumnas();

        while (!listo) {
            System.out.println(etiqueta + " (rango fila 0.." + (filas - 1) + ", columna 0.." + (cols - 1) + ")");
            System.out.print("Fila: ");
            String sf = sc.nextLine();
            System.out.print("Columna: ");
            String sc2 = sc.nextLine();

            try {
                int[] par = ManejadorEntrada.parseCoordenadas(sf, sc2, filas, cols);
                fila = par[0];
                col = par[1];

                // Extra: validar que la casilla aun este oculta
                if (!jm.validarMovimiento(fila, col)) {
                    System.out.println("Esa casilla ya esta descubierta o es invalida, vuelve a intentar ");
                } else {
                    listo = true;
                }
            } catch (Exception ex) {
                System.out.println("Entrada invalida: " + ex.getMessage());
            }
        }
        return new int[] { fila, col };
    }

    // Resultados y nueva partida

    private static void mostrarResultados(JuegoMemoria jm) {
        System.out.println("\n Marcador ");
        System.out.println(jm.getJugador1().getResumen());
        System.out.println(jm.getJugador2().getResumen());

        if (jm.juegoTerminado()) {
            System.out.println("\n Resultado Final ");
            var ganador = jm.determinarGanador();
            if (ganador == null) {
                System.out.println("Empate, wow...");
            } else {
                System.out.println("El Ganador esss: " + ganador.getNombre());
            }
        }
    }

    private static boolean preguntarNuevaPartida() {
        boolean listo = false;
        while (!listo) {
            System.out.print("¿Quieres iniciar una nueva partida? (S/N): ");
            String s = sc.nextLine();
            try {
                boolean r = ManejadorEntrada.parseConfirmacionSN(s);
                return r;
            } catch (Exception ex) {
                System.out.println("La entrada es invalida: " + ex.getMessage());
            }
        }
        return false; // nunca llega aqui por la estructura, pero si :D
    }
}
