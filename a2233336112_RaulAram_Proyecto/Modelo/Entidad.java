package Modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa una entidad con atributos dinámicos
 */
public class Entidad {
    private Map<String, Object> atributos;
    
    /**
     * Constructor que inicializa una entidad vacía
     */
    public Entidad() {
        this.atributos = new HashMap<>();
    }
    
    /**
     * Constructor que inicializa la entidad con un mapa de atributos existente
     * @param atributos Mapa con los valores iniciales de los atributos
     */
    public Entidad(Map<String, Object> atributos) {
        this.atributos = new HashMap<>(atributos);
    }
    
    /**
     * Establece un valor para un atributo
     * @param clave Nombre del atributo
     * @param valor Valor del atributo
     */
    public void setAtributo(String clave, Object valor) {
        atributos.put(clave, valor);
    }
    
    /**
     * Obtiene el valor de un atributo
     * @param clave Nombre del atributo
     * @return Valor del atributo
     */
    public Object getAtributo(String clave) {
        return atributos.get(clave);
    }
    
    /**
     * Obtiene todos los atributos de la entidad
     * @return Mapa con todos los atributos y sus valores
     */
    public Map<String, Object> getAtributos() {
        // Devolver una copia defensiva para evitar modificaciones externas
        return new HashMap<>(atributos);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Entidad{\n");
        for (Map.Entry<String, Object> entry : atributos.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
