package Cordiprogramas;

class Producto extends ItemVenta implements ImpuestoAplicable {
	 private int cantidad;

	    public Producto(String codigo, String nombre, double precio, int cantidad) {
	        super(codigo, nombre, precio);
	        this.cantidad = cantidad;
	    }

	    public int getCantidad() { return cantidad; }
	    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

	    @Override
	    public double calcularImpuestos() {
	        return (precio * IVA) + (precio * IEPS);
	    }

	    @Override
	    public String toString() {
	        return codigo + "," + nombre + "," + precio + "," + cantidad;
	    }
	}
