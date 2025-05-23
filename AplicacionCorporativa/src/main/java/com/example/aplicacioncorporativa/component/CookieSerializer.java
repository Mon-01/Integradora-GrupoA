/**
 * Clase utilitaria para serializar y deserializar listas de emails a/desde cadenas
 * codificadas en Base64, adecuadas para almacenamiento en cookies. Esta implementación
 * usa formato y análisis JSON manual para operación ligera sin la sobrecarga de una
 * biblioteca JSON completa.
 *
 * <p>Ejemplo de uso:
 * <pre>{@code
 * List<String> emails = Arrays.asList("usuario1@ejemplo.com", "usuario2@ejemplo.com");
 * String cookieValue = CookieSerializer.serialize(emails);
 * List<String> deserialized = CookieSerializer.deserialize(cookieValue);
 * }</pre>
 *
 * @see java.util.Base64 Para la implementación de codificación/decodificación
 */
package com.example.aplicacioncorporativa.component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CookieSerializer {

    /**
     * Serializa una lista de direcciones de email en una cadena codificada en Base64
     * que representa un arreglo JSON.
     *
     * <p>El proceso de serialización:
     * <ol>
     *   <li>Crea manualmente una cadena de arreglo JSON (ej. ["email1","email2"])
     *   <li>Convierte la cadena JSON a bytes usando el charset por defecto de la plataforma
     *   <li>Codifica los bytes usando codificación Base64 segura para URLs
     * </ol>
     *
     * @param emails Lista de direcciones de email a serializar (no puede ser nula)
     * @return Representación codificada en Base64 de los emails
     * @throws NullPointerException si el parámetro emails es nulo
     */
    public static String serialize(List<String> emails) {
        // Paso 1: Construcción manual del arreglo JSON
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < emails.size(); i++) {
            if (i > 0) sb.append(",");  // Añade separador después del primer elemento
            sb.append("\"").append(emails.get(i)).append("\"");  // Formato string JSON
        }
        sb.append("]");

        // Paso 2: Codificación Base64
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    /**
     * Deserializa una cadena codificada en Base64 (que representa un arreglo JSON)
     * de vuelta a una lista de direcciones de email.
     *
     * <p>El proceso de deserialización:
     * <ol>
     *   <li>Decodifica la cadena Base64 al JSON original
     *   <li>Analiza el arreglo JSON manualmente mediante:
     *     <ul>
     *       <li>Eliminando todos los corchetes y comillas
     *       <li>Dividiendo por comas
     *       <li>Recortando espacios en blanco
     *     </ul>
     *   <li>Retorna lista vacía para entrada nula/vacía
     * </ol>
     *
     * @param cookieValue Cadena codificada en Base64 (puede ser nula o vacía)
     * @return Lista de direcciones de email deserializadas (nunca nula)
     */
    public static List<String> deserialize(String cookieValue) {
        // Manejo de entrada nula/vacía (programación defensiva)
        if (cookieValue == null || cookieValue.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> emails = new ArrayList<>();
        try {
            // Paso 1: Decodificación Base64
            String json = new String(Base64.getDecoder().decode(cookieValue));

            // Paso 2: Análisis manual de JSON
            // Elimina todo el formato JSON: [],"
            String clean = json.replaceAll("[\\[\\]\"]", "");

            // Divide por comas y procesa cada elemento
            String[] parts = clean.split(",");
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    emails.add(trimmed);
                }
            }
        } catch (IllegalArgumentException e) {
            // Entrada Base64 inválida - retorna lista vacía
            System.err.println("Entrada Base64 inválida: " + cookieValue);
        }

        return emails;
    }
}
