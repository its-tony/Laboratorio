/*
 * Autor: Antony Portillo
 * Curso: CC2008 
 * Descripcion: Representa a un jugador con nombre y puntaje.
 * Notas:
 *  Puntaje inicia en 0 y se incrementa al acertar pares.
 * Fecha de creacion: [06/09/2025]
 * Ultima modificacion: [07/09/2025]
 */

public class Jugador {

    // mis atributos
    private String nombre;
    private int puntaje;

    // constructor
    public Jugador(String nombre) {
        this.nombre = (nombre != null) ? nombre : "Jugador";
        this.puntaje = 0;
    }


    public void sumarPunto() {
        this.puntaje++;
    }

    public void reiniciarPuntaje() {
        this.puntaje = 0;
    }

    // getters
    public String getNombre() {
        return nombre;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public String getResumen() {
        return nombre + " | Puntos: " + puntaje;
    }
}
