package Modelo;

/**
 * Clase que representa la definición de un atributo para las entidades
 */
public class DefinicionAtributo {
    private String nombre;
    private String tipo; // "String", "Integer", "Double", "Boolean", etc.
    
    /**
     * Constructor para crear una definición de atributo
     * @param nombre Nombre del atributo
     * @param tipo Tipo de dato del atributo
     */
    public DefinicionAtributo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    /**
     * Obtiene el nombre del atributo
     * @return Nombre del atributo
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el tipo de dato del atributo
     * @return Tipo de dato
     */
    public String getTipo() {
        return tipo;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
