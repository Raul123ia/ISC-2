package Librerias;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;


import Modelo.Producto;

public class ListaProducto extends Lista<Producto> {

 
	public DefaultTableModel getTablaModelo(ArrayList<String[]> lista )
	{   
	    // Limpiar la lista actual antes de procesarla
	    this.limpiar();
	    
	    // Actualizamos las columnas para incluir ID
	    String[] columnasnombre={"ID","SKU","Producto","Categoría","Precio Venta","Marca","Stock","Descripción"};
	    DefaultTableModel tabla = new DefaultTableModel(columnasnombre,0);
	    
	    if(lista != null && lista.size() > 0) {
	        for (String[] nodo : lista) {
	            // Proceso de conversión de String[] a Producto
	            try {
	                // Validar que tengamos suficientes datos
	                if(nodo.length >= 7) {
	                    String id = nodo[0];
	                    String sku = nodo[1];
	                    String producto = nodo[2];
	                    String idCategoria = nodo[3];
	                    double precioVenta = Double.parseDouble(nodo[4]);
	                    String marca = nodo[5];
	                    int stock = Integer.parseInt(nodo[6]);
	                    
	                    // El campo de descripción puede estar o no
	                    String descripcion = nodo.length > 7 ? nodo[7] : "";
	                    
	                    // Crear nuevo objeto Producto con el constructor actualizado
	                    Producto p = new Producto(id, sku, producto, idCategoria, precioVenta, marca, stock, descripcion);
	                    this.insertar(p);
	                }
	            } catch(Exception e) {
	                System.err.println("Error al procesar dato de producto: " + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    if(getLista() != null && getLista().size() > 0) {
	        for(Producto objeto : getLista()) {
	            // Usar los nuevos getters que coinciden con la implementación actualizada de Producto
	            tabla.addRow(new Object[]{
	                objeto.getIdProducto(), 
	                objeto.getSku(), 
	                objeto.getProducto(), 
	                objeto.getIdCategoria(), 
	                objeto.getPrecioVenta(), 
	                objeto.getMarca(), 
	                objeto.getStock(),
	                objeto.getDescripcion()
	            });
	        }
	    }
	    
	    return tabla;
	}
	
	/**
	 * Busca un producto por su ID
	 * @param idProducto ID del producto a buscar
	 * @return El producto encontrado o null si no existe
	 */
	public Producto buscarPorId(String idProducto) {
	    if (idProducto == null || idProducto.isEmpty() || getLista() == null) {
	        return null;
	    }
	    
	    for (Producto p : getLista()) {
	        if (idProducto.equals(p.getIdProducto())) {
	            return p;
	        }
	    }
	    
	    return null;
	}
	
	/**
	 * Busca un producto por su SKU
	 * @param sku SKU del producto a buscar
	 * @return El producto encontrado o null si no existe
	 */
	public Producto buscarPorSku(String sku) {
	    if (sku == null || sku.isEmpty() || getLista() == null) {
	        return null;
	    }
	    
	    for (Producto p : getLista()) {
	        if (sku.equals(p.getSku())) {
	            return p;
	        }
	    }
	    
	    return null;
	}
	
	/**
	 * Actualiza un producto existente con nuevos datos
	 * @param idProducto ID del producto a actualizar
	 * @param nuevosDatos Nuevos datos del producto
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizarProducto(String idProducto, Producto nuevosDatos) {
	    Producto productoExistente = buscarPorId(idProducto);
	    
	    if (productoExistente != null && nuevosDatos != null) {
	        // Actualizar todos los campos excepto el ID
	        productoExistente.setSku(nuevosDatos.getSku());
	        productoExistente.setProducto(nuevosDatos.getProducto());
	        productoExistente.setIdCategoria(nuevosDatos.getIdCategoria());
	        productoExistente.setPrecioVenta(nuevosDatos.getPrecioVenta());
	        productoExistente.setMarca(nuevosDatos.getMarca());
	        productoExistente.setStock(nuevosDatos.getStock());
	        productoExistente.setDescripcion(nuevosDatos.getDescripcion());
	        
	        return true;
	    }
	    
	    return false;
	}



	
}
