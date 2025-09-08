/*
Autor: Antony Portillo
 * Curso: CC2008 
 * Descripcion: Mantiene la matriz de emojis y el estado de visibilidad por casilla.
 * Notas:
 *  Casillas ocultas se representan con "." al construir la vista.
 * Fecha de creacion: [06/09/2025]
 * Ultima modificacion: [07/09/2025]
 */

public class Tablero {

    //  Constante visual para ocultos (la vista la usa via construirVista) 
    private static final String OCULTO = ".";

    
    private String[][] matriz;    // emojis colocados
    private boolean[][] visibles; // true = descubierta, false = oculta
    private int filas;
    private int columnas;

    // constructores
    /**
     * Crea un tablero con la matriz inicial de emojis (ya barajada).
     * Todas las casillas inician ocultas.
     */
    public Tablero(int filas, int columnas, String[][] matrizInicial) {
        this.filas = (filas > 0) ? filas : 4;
        this.columnas = (columnas > 0) ? columnas : 4;

        // Copia defensiva de la matriz inicial
        this.matriz = new String[this.filas][this.columnas];
        for (int f = 0; f < this.filas; f++) {
            for (int c = 0; c < this.columnas; c++) {
                this.matriz[f][c] = matrizInicial[f][c];
            }
        }

        // inicia aboslutamente todas las casillas como ocultas
        this.visibles = new boolean[this.filas][this.columnas];
        for (int f = 0; f < this.filas; f++) {
            for (int c = 0; c < this.columnas; c++) {
                this.visibles[f][c] = false;
            }
        }
    }

    // parte de la logica del juego

        /**
         * Intenta descubrir una casilla. Devuelve true si estaba oculta y pasa a visible.
         * Si ya estaba visible o la posicion es invalida, devuelve false.
         */
    public boolean descubrirCasilla(int fila, int columna) {
        if (!casillaDentro(fila, columna)) return false;
        if (visibles[fila][columna]) return false;
        visibles[fila][columna] = true;
        return true;
    }

    /** Vuelve a ocultar una casilla (usado cuando no hubo coincidencia). */
    public void ocultarCasilla(int fila, int columna) {
        if (!casillaDentro(fila, columna)) return;
        visibles[fila][columna] = false;
    }

    /** Comprueba si las dos posiciones forman un par (mismo emoji y visibles). */
    public boolean esPar(int f1, int c1, int f2, int c2) {
        if (!casillaDentro(f1, c1) || !casillaDentro(f2, c2)) return false;
        if (f1 == f2 && c1 == c2) return false; // no puede ser la misma casilla
        if (!visibles[f1][c1] || !visibles[f2][c2]) return false;
        String a = matriz[f1][c1];
        String b = matriz[f2][c2];
        return (a != null && a.equals(b));
    }

    /** Valida que la casilla esté en rango y aun oculta  */
    public boolean casillaValida(int fila, int columna) {
        return casillaDentro(fila, columna) && !visibles[fila][columna];
    }

    /** Devuelve true si todas las casillas ya están descubiertas. */
    public boolean tableroCompleto() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (!visibles[f][c]) return false;
            }
        }
        return true;
    }

    // esto es para la vista

    /**
     * es el tablero basicly
     * "." para casillas ocultas
     * muestra el emoji real para casillas visibles
     * La impresion real se hace en Principal.
     */
    public String construirVista() {
        StringBuilder sb = new StringBuilder();
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (visibles[f][c]) {
                    sb.append(matriz[f][c]);
                } else {
                    sb.append(OCULTO);
                }
                if (c < columnas - 1) sb.append(' ');
            }
            if (f < filas - 1) sb.append('\n');
        }
        return sb.toString();
    }

    // mis getters
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    private boolean casillaDentro(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }
 // no recuerdo que esto este en mi analisis pero ya no se me ocurre otra idea para ver las casillas :(
public String construirVistaCompleta() {
    StringBuilder sb = new StringBuilder();
    sb.append("   ");
    for (int c = 0; c < columnas; c++) {
        sb.append(c).append(" ");
    }
    sb.append("\n");

    for (int f = 0; f < filas; f++) {
        sb.append(f).append(" |");
        for (int c = 0; c < columnas; c++) {
            sb.append(matriz[f][c]).append("|"); 
        }
        sb.append("\n");
    }
    return sb.toString();
}

}
