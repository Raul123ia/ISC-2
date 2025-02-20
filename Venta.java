package Cordiprogramas;
import java.util.*;
import java.io.*;

public class Venta {
	 private String idTicket;
	    private Date fecha;
	    private List<Producto> productos = new ArrayList<>();

	    public Venta(String idTicket, Date fecha) {
	        this.idTicket = idTicket;
	        this.fecha = fecha;
	    }

	    public void agregarProducto(Producto producto, int cantidad) {
	        if (producto.getCantidad() >= cantidad) {
	            productos.add(new Producto(producto.getCodigo(), producto.getNombre(), producto.getPrecio(), cantidad));
	            producto.setCantidad(producto.getCantidad() - cantidad);
	        } else {
	            System.out.println("Stock insuficiente.");
	        }
	    }

	    public double calcularTotal() {
	        return productos.stream()
	                .mapToDouble(p -> p.getPrecio() + p.calcularImpuestos())
	                .sum();
	    }

	    public void listarProductos() {
	        productos.forEach(p -> System.out.println(p.getNombre() + " | Precio: " + p.getPrecio() + " | Impuestos: " + p.calcularImpuestos()));
	        System.out.println("Total: " + calcularTotal());
	    }
	}