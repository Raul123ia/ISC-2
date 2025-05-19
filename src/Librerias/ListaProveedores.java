package Librerias;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import Modelo.Proveedor;

public class ListaProveedores extends Lista<Proveedor> {

	public DefaultTableModel getTablaModelo(ArrayList<String[]> lista) {
		String[] columnasnombre = {"ID Proveedor", "Nombre", "Estado"};
		DefaultTableModel tabla = new DefaultTableModel(columnasnombre, 0) {
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
				this.insertar(new Proveedor(nodo[0], nodo[1], activo));
			}
			
			if(getLista().size() > 0) {
				for(Proveedor objeto:getLista()) {
					tabla.addRow(new Object[]{
						objeto.getIdProveedor(),
						objeto.getProveedor(),
						objeto.isActivo() ? "Activo" : "Inactivo"
					});
				}
			}
		}
		
		return tabla;
	}
	
	/**
	 * Obtiene un modelo de tabla solo con proveedores activos
	 * @param lista Lista de datos de proveedores
	 * @return Modelo de tabla con proveedores activos
	 */
	public DefaultTableModel getTablaModeloActivos(ArrayList<String[]> lista) {
		String[] columnasnombre = {"ID Proveedor", "Nombre"};
		DefaultTableModel tabla = new DefaultTableModel(columnasnombre, 0) {
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
					this.insertar(new Proveedor(nodo[0], nodo[1], activo));
				}
			}
			
			if(getLista().size() > 0) {
				for(Proveedor objeto:getLista()) {
					tabla.addRow(new Object[]{
						objeto.getIdProveedor(),
						objeto.getProveedor()
					});
				}
			}
		}
		
		return tabla;
	}
}
