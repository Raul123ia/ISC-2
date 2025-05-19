package Librerias;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import Modelo.Categoria;

public class ListaCategoria extends Lista<Categoria> {

 
	public DefaultTableModel getTablaModelo(ArrayList<String[]> lista )
	{   String[] columnasnombre={"Id Categoria","    Categoria   ", "Estado"};
		DefaultTableModel tabla = new DefaultTableModel(columnasnombre,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hacer la tabla no editable
			}
		};
		
		// Limpiar la lista actual antes de agregar nuevos elementos
		this.limpiar();
		
		if(lista != null && lista.size() > 0) {
			for (String[] nodo:lista) {
				boolean activo = true; // Por defecto está activo
				if (nodo.length > 2 && nodo[2] != null) {
					activo = "1".equals(nodo[2]) || "true".equalsIgnoreCase(nodo[2]);
				}
				this.insertar(new Categoria(nodo[0], nodo[1], activo));
			}
			
			if(getLista().size() > 0) {
				for(Categoria objeto:getLista()) {
					tabla.addRow(new Object[]{
						objeto.getId(),
						objeto.getEtiqueta(),
						objeto.isActivo() ? "Activo" : "Inactivo"
					});
				}
			}
		}
		
		return tabla;
	}
	
	/**
	 * Obtiene un modelo de tabla solo con categorías activas
	 * @param lista Lista de datos de categorías
	 * @return Modelo de tabla con categorías activas
	 */
	public DefaultTableModel getTablaModeloActivas(ArrayList<String[]> lista) {
		String[] columnasnombre={"Id Categoria","    Categoria   "};
		DefaultTableModel tabla = new DefaultTableModel(columnasnombre,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hacer la tabla no editable
			}
		};
		
		// Limpiar la lista actual antes de agregar nuevos elementos
		this.limpiar();
		
		if(lista != null && lista.size() > 0) {
			for (String[] nodo:lista) {
				boolean activo = true; // Por defecto está activo
				if (nodo.length > 2 && nodo[2] != null) {
					activo = "1".equals(nodo[2]) || "true".equalsIgnoreCase(nodo[2]);
				}
				if (activo) {
					this.insertar(new Categoria(nodo[0], nodo[1], activo));
				}
			}
			
			if(getLista().size() > 0) {
				for(Categoria objeto:getLista()) {
					tabla.addRow(new Object[]{
						objeto.getId(),
						objeto.getEtiqueta()
					});
				}
			}
		}
		
		return tabla;
	}
}
