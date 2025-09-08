/*
 * Autor: Antony Portillo
 * Curso: CC2008 
 * Descripcion: Metodos estaticos para validar y convertir entradas de texto.
 * Notas:
 *  - Usa try/catch para evitar que entradas invalidas rompan el programa.
 * Fecha de creacion: [06/09/2025]
 * Ultima modificacion: [07/09/2025]
 */

public class ManejadorEntrada {

    private ManejadorEntrada() { }

    public static int parseEnteroEnRango(String input, int min, int max) {
        try {
            int n = Integer.parseInt(input.trim());
            if (n < min || n > max) {
                throw new IllegalArgumentException(
                    "Ese valor está fuera de rango: " + n + " (esperado " + min + ".." + max + ")"
                );
            }
            return n;
        } catch (NumberFormatException ex) {
            //se vuelve a preguntar
            throw new NumberFormatException("Debes de ingresar un numero entero valido");
        }
    }

    /**
     * Valida texto no vacio; retorna el texto recortado.
     * @throws IllegalArgumentException si es vacio
     */
    public static String parseTextoNoVacio(String input) {
        String t = (input == null) ? "" : input.trim();
        if (t.isEmpty()) {
            throw new IllegalArgumentException("El texto no puede estar vacioooo");
        }
        return t;
    }

   
    public static boolean parseConfirmacionSN(String input) {
        String t = parseTextoNoVacio(input).toUpperCase();
        if (t.equals("S")) return true;
        if (t.equals("N")) return false;
        throw new IllegalArgumentException("Responde solo 'S' o 'N' :D");
    }

    /**
     * Parsea la dimension del tablero. Solo acepta 4 o 6.
     * @throws IllegalArgumentException si no es 4 o 6
     */
    public static int parseDimensionTablero(String input) {
        int dim = parseEnteroEnRango(input, 4, 6);
        if (dim != 4 && dim != 6) {
            throw new IllegalArgumentException("La dimension solo puede ser 4 o 6.");
        }
        return dim;
    }
    public static int[] parseCoordenadas(String inputFila, String inputCol, int filas, int cols) {
        int f = parseEnteroEnRango(inputFila, 0, filas - 1);
        int c = parseEnteroEnRango(inputCol, 0, cols - 1);
        return new int[] { f, c };
    }
}
//todo esto lo vimos con el battleshipppp 
