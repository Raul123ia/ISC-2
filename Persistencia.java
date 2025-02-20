package Cordiprogramas;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

 
class Persistencia<T> implements PersistenciaDatos<T> {
	public void guardarCSV(String archivo, List<T> lista) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (T item : lista) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> cargarCSV(String archivo) {
        List<T> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Aquí se debería implementar la lógica específica de conversión según el tipo de objeto
                System.out.println("Carga de CSV no implementada completamente");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void guardarJSON(String archivo, List<T> lista) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write("[\n"); // Iniciar JSON como una lista
            for (int i = 0; i < lista.size(); i++) {
                writer.write(convertirAJSON(lista.get(i))); // Convertir objeto a JSON manualmente
                if (i < lista.size() - 1) {
                    writer.write(",");
                }
                writer.newLine();
            }
            writer.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> cargarJSON(String archivo) {
        List<T> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            StringBuilder json = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                json.append(linea);
            }
            // Aquí se debe convertir manualmente de JSON a objetos
            lista = convertirDesdeJSON(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para convertir un objeto a formato JSON (simulación básica)
    private String convertirAJSON(T item) {
        return "{ \"objeto\": \"" + item.toString() + "\" }";
    }

    // Método para convertir una cadena JSON a una lista de objetos
    private List<T> convertirDesdeJSON(String json) {
        List<T> lista = new ArrayList<>();
        // Simulación de conversión JSON a objetos
        System.out.println("Conversión de JSON no implementada completamente");
        return lista;
    }
}