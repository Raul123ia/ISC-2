package singleton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class PersistenciaServiceSingleton {
    private static PersistenciaServiceSingleton instancia;
    private Gson gson;

    private PersistenciaServiceSingleton() {
        gson = new Gson();
    }

    public static PersistenciaServiceSingleton getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaServiceSingleton();
        }
        return instancia;
    }

    private static final String DIRECTORIO_DATOS = "datos";
    
    public void guardarEntidad(List<Map<String, Object>> entidades, String nombreArchivo) {
        // Imprimir los datos antes de filtrar
        System.out.println("--- GUARDANDO ENTIDADES ---");
        System.out.println("Entidades recibidas: " + entidades.size());
        for (int i = 0; i < entidades.size(); i++) {
            System.out.println("Entidad " + (i+1) + ": " + entidades.get(i));
        }
        
        // Eliminar mapas vacíos y crear copias para evitar problemas con referencias
        List<Map<String, Object>> entidadesNoVacias = new ArrayList<>();
        for (Map<String, Object> entidad : entidades) {
            if (entidad != null && !entidad.isEmpty()) {
                // Crear una copia del mapa para evitar problemas con referencias
                Map<String, Object> copia = new HashMap<>(entidad);
                entidadesNoVacias.add(copia);
            }
        }
        
        System.out.println("Entidades después de filtrar vacías: " + entidadesNoVacias.size());
        
        // Si no hay entidades para guardar, crear una entidad de ejemplo
        if (entidadesNoVacias.isEmpty()) {
            System.out.println("No hay entidades no vacías para guardar. Se creará una entidad de ejemplo");
            Map<String, Object> entidadEjemplo = new HashMap<>();
            entidadEjemplo.put("nombre", "Ejemplo");
            entidadEjemplo.put("descripcion", "Esta es una entidad de ejemplo");
            entidadesNoVacias.add(entidadEjemplo);
        }
        
        // Crear directorio datos si no existe
        File directorio = new File(DIRECTORIO_DATOS);
        if (!directorio.exists()) {
            directorio.mkdirs();
            System.out.println("Directorio creado: " + directorio.getAbsolutePath());
        }
        
        // Crear ruta completa al archivo
        File archivo = new File(directorio, nombreArchivo);
        System.out.println("Guardando en: " + archivo.getAbsolutePath());
        
        // Guardar solo entidades no vacías
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(entidadesNoVacias, writer);
            System.out.println("Datos guardados exitosamente");
            
            // Forzar la escritura al disco
            writer.flush();
            
            // Verificar el contenido guardado
            try (FileReader reader = new FileReader(archivo)) {
                Type tipoLista = new TypeToken<List<Map<String, Object>>>() {}.getType();
                List<Map<String, Object>> verificacion = gson.fromJson(reader, tipoLista);
                if (verificacion != null) {
                    System.out.println("Verificación: se guardaron " + verificacion.size() + " entidades");
                } else {
                    System.out.println("Advertencia: La verificación retornó null");
                }
            } catch (Exception e) {
                System.err.println("Error al verificar datos guardados: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }
        
        /**
         * Carga las entidades desde un archivo JSON
         * 
         * @param nombreArchivo Nombre del archivo a cargar
         * @return Lista de mapas con los datos de las entidades
         */
        public List<Map<String, Object>> cargarEntidades(String nombreArchivo) {
            // Crear ruta completa al archivo
            File directorio = new File(DIRECTORIO_DATOS);
            File archivo = new File(directorio, nombreArchivo);
            
            System.out.println("Intentando cargar desde: " + archivo.getAbsolutePath());
            
            // Si el archivo no existe o no se puede leer, retornar una lista vacía
            if (!archivo.exists() || !archivo.canRead()) {
                System.out.println("El archivo no existe o no se puede leer. Se devolverá una lista vacía.");
                return new ArrayList<>();
            }
            
            try (FileReader reader = new FileReader(archivo)) {
                // Definir el tipo para deserialización
                Type tipoLista = new TypeToken<List<Map<String, Object>>>() {}.getType();
                
                // Deserializar desde JSON a lista de mapas
                List<Map<String, Object>> entidades = gson.fromJson(reader, tipoLista);
                
                // Si el resultado es nulo, retornar lista vacía
                if (entidades == null) {
                    System.out.println("El archivo existe pero no contiene datos válidos. Se devolverá una lista vacía.");
                    return new ArrayList<>();
                }
                
                System.out.println("Se cargaron " + entidades.size() + " entidades del archivo JSON");
                
                // Mostrar información para depuración
                for (int i = 0; i < entidades.size(); i++) {
                    System.out.println("Entidad " + (i+1) + " cargada: " + entidades.get(i));
                }
                
                return entidades;
                
            } catch (Exception e) {
                System.err.println("Error al cargar entidades: " + e.getMessage());
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

   
}