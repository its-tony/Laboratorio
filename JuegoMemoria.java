/*
 * Autor: Antony Portillo
 * Curso: CC2008 
 * Descripcion: Controla la logica del juego: turnos, validaciones, aciertos,
 *              conteo de movimientos y fin de partida.
 * Notas:
 * Mantiene bandera de ultimo acierto para que la vista decida si cambia turno.
 * Fecha de creacion: [06/09/2025]
 * Ultima modificacion: [07/09/2025]
 */

public class JuegoMemoria {

    // -Estado principal 
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private int turnoActual;     // 1 = jugador1, 2 = jugador2
    private int movimientos;     // cantidad de jugadas (pares intentados)
    private boolean ultimoAcierto; // true si la ultima jugada fue par correcto

    // constructor
    public JuegoMemoria(Tablero tablero, Jugador jugador1, Jugador jugador2) {
        this.tablero = tablero;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.turnoActual = 1;
        this.movimientos = 0;
        this.ultimoAcierto = false;
    }

   
    public Jugador getJugadorActual() {
        return (turnoActual == 1) ? jugador1 : jugador2;
    }
    public Jugador getJugador1() { return jugador1; }
    public Jugador getJugador2() { return jugador2; }
    public int getTurnoActual() { return turnoActual; }
    public int getMovimientos() { return movimientos; }
    public boolean fueUltimoAcierto() { return ultimoAcierto; }

    // turnos
    public void cambiarTurno() {
        turnoActual = (turnoActual == 1) ? 2 : 1;
    }

    // validar
    public boolean validarMovimiento(int fila, int columna) {
        return tablero.casillaValida(fila, columna);
    }

    public boolean jugarTurno(int f1, int c1, int f2, int c2) {
        if (f1 == f2 && c1 == c2) { // no permitir misma casilla dos veces
            ultimoAcierto = false;
            return false;
        }
        if (!validarMovimiento(f1, c1)) {
            ultimoAcierto = false;
            return false;
        }

        boolean ok1 = tablero.descubrirCasilla(f1, c1);
        if (!ok1) { // ya visible o invalida por alguna razon
            ultimoAcierto = false;
            return false;
        }

        if (!validarMovimiento(f2, c2)) {
            tablero.ocultarCasilla(f1, c1);
            ultimoAcierto = false;
            return false;
        }

        // Descubre la segunda
        boolean ok2 = tablero.descubrirCasilla(f2, c2);
        if (!ok2) {
            // revertir la primera si la segunda no se pudo descubrir
            tablero.ocultarCasilla(f1, c1);
            ultimoAcierto = false;
            return false;
        }

        movimientos++;
        boolean esPar = tablero.esPar(f1, c1, f2, c2);

        if (esPar) {
            getJugadorActual().sumarPunto();
            ultimoAcierto = true; // mantiene turno
        } else {

ultimoAcierto = false;

        }
        return true;
    }

   //fin del juego
    public boolean juegoTerminado() {
        return tablero.tableroCompleto();
    }
// da el ganador o null si empate
    public Jugador determinarGanador() {
        int p1 = jugador1.getPuntaje();
        int p2 = jugador2.getPuntaje();
        if (p1 > p2) return jugador1;
        if (p2 > p1) return jugador2;
        return null; // empate
    }

    // reiniciar juego
    public void reiniciarJuego(Tablero nuevoTablero) {
        this.tablero = nuevoTablero;
        this.jugador1.reiniciarPuntaje();
        this.jugador2.reiniciarPuntaje();
        this.turnoActual = 1;
        this.movimientos = 0;
        this.ultimoAcierto = false;
    }
    public Tablero getTablero() { return tablero; }
}
