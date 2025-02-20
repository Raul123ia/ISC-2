package Cordiprogramas;

import java.util.ArrayList;

public class Ventas {
	private ArrayList<Venta_Por_Unidad> venta;
	private String fecha;
	private int total;
	
	public Ventas(ArrayList<Venta_Por_Unidad> venta, String fecha) {
		super();
		this.venta = venta;
		this.fecha = fecha;
		for (Venta_Por_Unidad ventas: venta) {
			this.total = this.total + ventas.getPrecio_total();
		}
	}
	
	
	public ArrayList<Venta_Por_Unidad> getVenta() {
		return venta;
	}


	public void setVenta(ArrayList<Venta_Por_Unidad> venta) {
		this.venta = venta;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Ventas() {
        this.venta = new ArrayList<>();
    }

	public void agregarProducto(Productos producto, int cantidad) {
        venta.add(new Venta_Por_Unidad(producto, cantidad));
    }

    // Eliminar un producto del ticket
    public void eliminarProducto(int indice) {
        if (indice >= 0 && indice < venta.size()) {
            venta.remove(indice);
	        System.out.println("Producto eliminado del ticket.");
        } else {
            System.out.println("Índice fuera de rango. No se eliminó ningún producto.");
        }
    }
	
}
