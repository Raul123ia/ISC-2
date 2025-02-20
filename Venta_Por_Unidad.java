package Cordiprogramas;

public class Venta_Por_Unidad extends Productos {
	private int cant;
	private int precio_total;
	public Venta_Por_Unidad(Productos prod, int cant) {
		super(prod.getId(),prod.getNombre(),prod.getPrecio());
		this.cant = cant;
		this.precio_total = this.cant * super.getPrecio();
				
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
	public int getPrecio_total() {
		return precio_total;
	}
	public void setPrecio_total(int precio_total) {
		this.precio_total = precio_total;
	}

	

}