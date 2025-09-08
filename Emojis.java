/*
 * Autor: Antony Portillo
 * Curso: CC2008 
 * Descripcion: Mantiene una lista base de emojis de comida, crea la baraja
 * de pares y la baraja/distribuye para llenar el tablero.
 * Notas:
 * Fecha de creacion: [06/09/2025]
 * Ultima modificacion: [07/09/2025]
 */

import java.util.Random;

public class Emojis {

    // Lista base de emojis de comida 
    private String[] base = new String[] {
        "🍎","🍌","🍓","🍇","🍉","🍍","🥑","🥕","🌽",
        "🍕","🍔","🌭","🍟","🌮","🌯","🍣","🍜","🍩",
        "🍪","🍫","🍰","🧀","🍗","🥞"
    };

    private Random rng;

    // constructor
    public Emojis() {
        this.rng = new Random();
    }

    // mis métodos

    /**
     * Crea una baraja lineal de pares de emojis, barajada.
     * totalCasillas debe ser par y > 0.
     */
    public String[] crearBaraja(int totalCasillas) {
        if (totalCasillas <= 0 || totalCasillas % 2 != 0) {
            // Ajuste defensivo: si no es par o invalido, retornamos el arreglo vacio
            return new String[0];
        }

        int paresNecesarios = totalCasillas / 2;

        
        if (paresNecesarios > base.length) {
   
        }

        String[] baraja = new String[totalCasillas];
        int idx = 0;

       
        for (int i = 0; i < paresNecesarios; i++) {
            String emoji = base[i % base.length];
            baraja[idx++] = emoji;
            baraja[idx++] = emoji;
        }

        mezclar(baraja);
        return baraja;
        }

    /**
     * Construye una matriz filas x columnas con la baraja barajada.
     */  
    public String[][] crearMatrizBarajada(int filas, int columnas) {
        int total = filas * columnas;
        String[] lineal = crearBaraja(total);
        String[][] matriz = new String[filas][columnas];

        int k = 0;
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                matriz[f][c] = lineal[k++];
            }
        }
        return matriz;
    }

   
    public String[] getBase() {
        String[] copia = new String[base.length];
        System.arraycopy(base, 0, copia, 0, base.length);
        return copia;
    }

    public void setBase(String[] nuevos) {
        if (nuevos != null && nuevos.length >= 8) {
            this.base = new String[nuevos.length];
            System.arraycopy(nuevos, 0, this.base, 0, nuevos.length);
        }
    }

    //esto lo aprendí del battleship :D

    private void mezclar(String[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            String tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
