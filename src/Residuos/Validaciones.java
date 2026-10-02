package Residuos;

import java.time.LocalDate;

/** Validaciones comunes para los datos capturados en los formularios. */
public final class Validaciones {

    // Clase de utilidades: no hace falta crear objetos de ella.
    private Validaciones() { }

    /** Comprueba que un campo obligatorio no esté vacío. */
    public static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException("El campo '" + campo + "' es obligatorio.");
        return valor.trim();
    }

    /** Convierte texto a double y genera un mensaje entendible si falla. */
    public static double numero(String valor, String campo) {
        try {
            return Double.parseDouble(texto(valor, campo));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser numérico.");
        }
    }

    /** Valida un número y evita valores negativos. */
    public static double noNegativo(String valor, String campo) {
        double n = numero(valor, campo);
        if (n < 0) throw new IllegalArgumentException("El campo '" + campo + "' no puede ser negativo.");
        return n;
    }

    /** Comprueba una fecha con formato AAAA-MM-DD. */
    public static void fecha(String valor, String campo) {
        valor = texto(valor, campo);
        try {
            LocalDate.parse(valor);
        } catch (Exception e) {
            throw new IllegalArgumentException("La fecha de '" + campo + "' debe ser AAAA-MM-DD y válida.");
        }
    }

    /** Validación básica del formato de teléfono. */
    public static void telefono(String valor) {
        valor = texto(valor, "Teléfono");
        if (!valor.matches("[0-9+()\\- ]{7,20}"))
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
    }
}
