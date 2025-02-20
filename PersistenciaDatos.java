package Cordiprogramas;
import java.util.*;
import java.io.*;


public interface PersistenciaDatos<T> {
	 void guardarCSV(String archivo, List<T> lista);
	    List<T> cargarCSV(String archivo);
	    void guardarJSON(String archivo, List<T> lista);
	    List<T> cargarJSON(String archivo);
}
