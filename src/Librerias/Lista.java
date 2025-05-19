
package Librerias;

import java.util.ArrayList;
import java.util.Collection;

import Modelo.Producto;

public class Lista<T> {
    private ArrayList<T> lista;

    public Lista() {
        lista = new ArrayList<T>();
    }

    public int size() {
        return lista.size();
    }

    public boolean vacia() {
        return lista.isEmpty();
    }

    public boolean insertar(T objeto) {
        return lista.add(objeto);
    }

    public boolean eliminar(T objeto) {
        return lista.remove(objeto);
    }

    public boolean modificar(T objetoNuevo) {
        // Buscar el objeto por equals y reemplazarlo
        if (objetoNuevo != null) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).equals(objetoNuevo)) {
                    lista.set(i, objetoNuevo);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<T> getLista() {
        return this.lista;
    }

    public void setLista(ArrayList<T> lista) {
        this.lista = lista;
    }
    
    // Buscar un objeto en la lista
    public T buscar(T objeto) {
        int indice = lista.indexOf(objeto);
        return indice >= 0 ? lista.get(indice) : null;
    }
    
    // Limpiar toda la lista
    public void limpiar() {
        lista.clear();
    }
    
    // Verificar si un objeto existe en la lista
    public boolean existe(T objeto) {
        return lista.contains(objeto);
    }
    
    // Agregar una colección de objetos a la lista
    public boolean insertarTodos(Collection<T> objetos) {
        return lista.addAll(objetos);
    }
}
