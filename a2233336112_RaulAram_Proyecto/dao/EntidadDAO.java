package dao;

import Modelo.Entidad;
import singleton.PersistenciaServiceSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase para el acceso a datos de entidades
 */
public class EntidadDAO {
    private static final String ARCHIVO_ENTIDADES = "entidades.json";
    private PersistenciaServiceSingleton persistencia;
    private List<Map<String, Object>> entidades;
    
    /**
     * Constructor que inicializa el DAO y carga los datos
     */
    public EntidadDAO() {
        persistencia = PersistenciaServiceSingleton.getInstancia();
        cargarEntidades();
    }
    
    /**
     * Carga las entidades desde el archivo JSON
     */
    private void cargarEntidades() {
        entidades = persistencia.cargarEntidades(ARCHIVO_ENTIDADES);
        if (entidades == null) {
            entidades = new ArrayList<>();
        }
        System.out.println("DAO: Se cargaron " + entidades.size() + " entidades");
    }
    
    /**
     * Guarda las entidades en el archivo JSON
     */
    public void guardarEntidades() {
        // Verificación adicional
        if (entidades == null) {
            entidades = new ArrayList<>();
        }
        
        System.out.println("Guardando " + entidades.size() + " entidades en " + ARCHIVO_ENTIDADES);
        
        // Guardar las entidades
        persistencia.guardarEntidad(entidades, ARCHIVO_ENTIDADES);
        
        // Validar que se hayan guardado
        List<Map<String, Object>> verificacion = persistencia.cargarEntidades(ARCHIVO_ENTIDADES);
        if (verificacion != null) {
            System.out.println("Verificación post-guardado: se guardaron " + verificacion.size() + " entidades");
        } else {
            System.out.println("Advertencia: Verificación de guardado falló");
        }
    }
        
        /**
         * Guarda una lista de entidades (método alternativo para compatibilidad)
         * @param listaEntidades Lista de entidades a guardar
         */
        public void guardar(List<Map<String, Object>> listaEntidades) {
            this.entidades = new ArrayList<>(listaEntidades);
            guardarEntidades();
        }
    
    /**
     * Obtiene todas las entidades
     * @return Lista de mapas con los datos de las entidades
     */
    public List<Map<String, Object>> obtenerEntidades() {
        if (entidades == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entidades);
    }
    
    /**
     * Carga y devuelve las entidades desde el archivo JSON
     * @return Lista de mapas con los datos de las entidades
     */
    public List<Map<String, Object>> cargar() {
        cargarEntidades(); // Refrescar las entidades desde el archivo
        return obtenerEntidades();
    }
    
    /**
     * Agrega una nueva entidad
     * @param entidad La entidad a agregar
     */
    public void agregarEntidad(Entidad entidad) {
        entidades.add(entidad.getAtributos());
        guardarEntidades();
    }
    
    /**
     * Actualiza una entidad existente
     * @param indice Índice de la entidad a actualizar
     * @param entidad Nueva información de la entidad
     */
    public void actualizarEntidad(int indice, Entidad entidad) {
        if (indice >= 0 && indice < entidades.size()) {
            entidades.set(indice, entidad.getAtributos());
            guardarEntidades();
        }
    }
    
    /**
     * Elimina una entidad
     * @param indice Índice de la entidad a eliminar
     */
    public void eliminarEntidad(int indice) {
        if (indice >= 0 && indice < entidades.size()) {
            entidades.remove(indice);
            guardarEntidades();
        }
    }
}
