package Modelo;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class PersistenciaService {
    private static final String ARCHIVO_JSON = "entidades.json";

    public void guardarEntidades(List<Entidad> entidades) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(ARCHIVO_JSON);
        gson.toJson(entidades, writer);
        writer.close();
    }

    public List<Entidad> cargarEntidades() throws IOException {
        List<Entidad> entidades = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists()) return entidades;

        Gson gson = new Gson();
        Reader reader = new FileReader(archivo);
        Entidad[] array = gson.fromJson(reader, Entidad[].class);
        reader.close();
        entidades = Arrays.asList(array);
        return entidades;
    }
}