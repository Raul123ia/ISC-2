package Cordiprogramas;
import java.util.*;
import java.io.*;

public class Inventario {
    private List<Producto> productos;
    private Persistencia<Producto> persistencia;
    private static final String ARCHIVO_CSV = "productos.csv";
    private static final String ARCHIVO_JSON = "productos.json";

    public Inventario() {
        persistencia = new Persistencia<>();
        productos = persistencia.cargarJSON(ARCHIVO_JSON);
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarDatos();
    }

    public void eliminarProducto(String codigo) {
        productos.removeIf(p -> p.getCodigo().equals(codigo));
        guardarDatos();
    }

    public void listarProductos() {
        productos.forEach(System.out::println);
    }

    private void guardarDatos() {
        persistencia.guardarJSON(ARCHIVO_JSON, productos);
        persistencia.guardarCSV(ARCHIVO_CSV, productos);
    }
}