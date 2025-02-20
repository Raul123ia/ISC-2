package Cordiprogramas;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class Proyecto_Integrador {
	
	static ArrayList<Productos> prod = new ArrayList<Productos>();
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static int res;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int i,ii,iii,opcion;
		i = 1;
		prod = productos(prod);
				
		do {
            opcion = interfaz_principal();
            switch (opcion) {
                case 1:
                    prod = gestion_de_productos(prod);
                    break;
                case 2:
                    punto_de_venta();
                    break;
                case 3:
                    System.out.println("Gracias por utilizar el sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 3);
	}

	public static int interfaz_principal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Productos");
        System.out.println("2. Punto de Venta");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        try {
            int opcion = Integer.parseInt(bf.readLine());
            return opcion;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
	
	public static ArrayList<Productos> gestion_de_productos(ArrayList<Productos> productos) {
	    System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
	    System.out.println("1. Modificar un producto");
	    System.out.println("2. Mostrar lista de productos");
	    System.out.print("Seleccione una opción: ");
	    try {
	        int opcion = Integer.parseInt(bf.readLine());
	        switch (opcion) {
	            case 1:
	                prod = modificar_producto(productos);
	                break;
	            case 2:
	                mostrar_productos(productos);
	                break;
	            default:
	                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return prod;
	}
		
	public static String capturarid() {
		String id = "";
		
		return id;
	}
	
	public static ArrayList<Productos> productos(ArrayList<Productos> p) {
		p.add(new Productos("001","Cafe late grande     ",70));
		p.add(new Productos("002","Cafe late chico      ",50));
		p.add(new Productos("003","Cafe capuchino grande",70));
		p.add(new Productos("004","Cafe capuchino chico ",50));
		p.add(new Productos("005","Baguete Clasico      ",90));
		p.add(new Productos("006","Baget con Avellana   ",95));
		p.add(new Productos("007","Café moka frapuchino ",56));
		p.add(new Productos("008","Cafe expreso         ",40));
		p.add(new Productos("009","Baguette Italiano    ",110));
		p.add(new Productos("010","Arranciata Natural   ",45));
		return p;
	}
		
    public static ArrayList<Productos> modificar_producto(ArrayList<Productos> prod) throws NumberFormatException, IOException {
        System.out.println("MODIFICAR PRODUCTOS");

        do {
            System.out.println("1. Editar ID");
            System.out.println("2. Editar NOMBRE");
            System.out.println("3. Editar Precio");
            System.out.println("4. Salir");
            res = Integer.parseInt(bf.readLine());

            switch (res) {
                case 1:
                    editar_id(prod);
                    break;
                case 2:
                    editar_nombre(prod);
                    break;
                case 3:
                    editar_precio(prod);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }

        } while (res != 4);
        return prod;
    }

    public static void editar_id(ArrayList<Productos> prod) throws IOException {
        System.out.print("Ingrese el código del producto que desea editar: ");
        String id = bf.readLine();
        System.out.print("Ingrese el nuevo código: ");
        String nuevo_id = bf.readLine();
        if (existe_id(prod, nuevo_id)) {
            System.out.println("El ID ingresado ya existe. No se puede repetir.");
            return;
        }

        for (Productos producto : prod) {
            if (producto.getId().equals(id)) {
                producto.setId(nuevo_id);
                System.out.println("ID modificado exitosamente.");
                return;
            }
        }
        System.out.println("No se encontró ningún producto con el ID proporcionado.");
    }

    public static void editar_nombre(ArrayList<Productos> prod) throws IOException {
        System.out.print("Ingrese el código del producto que desea editar: ");
        String id = bf.readLine();
        System.out.print("Ingrese el nuevo nombre: ");
        String nuevo_nombre = bf.readLine();

        if (existe_nombre(prod, nuevo_nombre)) {
            System.out.println("El nombre ingresado ya está siendo utilizado por otro producto. No se puede repetir.");
            return;
        }

        for (Productos producto : prod) {
            if (producto.getId().equals(id)) {
                producto.setNombre(nuevo_nombre);
                System.out.println("Nombre modificado exitosamente.");
                return;
            }
        }
        System.out.println("No se encontró ningún producto con el ID proporcionado.");
    }

    public static void editar_precio(ArrayList<Productos> prod) throws IOException {
        System.out.print("Ingrese el código del producto que desea editar: ");
        String id = bf.readLine();
        System.out.print("Ingrese el nuevo precio: ");
        int nuevo_precio = Integer.parseInt(bf.readLine());

        for (Productos producto : prod) {
            if (producto.getId().equals(id)) {
                producto.setPrecio(nuevo_precio);
                System.out.println("Precio modificado exitosamente.");
                return;
            }
        }
        System.out.println("No se encontró ningún producto con el ID proporcionado.");
    }

    public static boolean existe_id(ArrayList<Productos> prod, String id) {
        for (Productos producto : prod) {
            if (producto.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existe_nombre(ArrayList<Productos> prod, String nombre) {
        for (Productos producto : prod) {
            if (producto.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

	public static void mostrar_productos(ArrayList<Productos> productos) {
        System.out.println("=== LISTA DE PRODUCTOS ===");
        System.out.println("Código \t Nombre \t Precio");
        for (Productos producto : productos) {
            System.out.println(producto.getId() + "\t" + producto.getNombre() + "\t\t" + producto.getPrecio());
        }
	}
	
	public static void punto_de_venta() throws NumberFormatException, IOException {
	    ArrayList<Productos> p = new ArrayList<Productos>();
	    Ventas venta = new Ventas();
	    System.out.println("PUNTO DE VENTA");

	    do {
	        System.out.println("1. Agregar producto al ticket");
	        System.out.println("2. Eliminar productos del ticket");
	        System.out.println("3. Ver el detalle del ticket");
	        System.out.println("4. Generar ticket");
	        System.out.println("5. Salir del punto de venta");
	        System.out.print("Seleccione una opción: ");
	        int opcion = Integer.parseInt(bf.readLine());

	        switch (opcion) {
	            case 1:
	                venta = agregar_producto(venta);
	                break;
	            case 2:
	                venta = eliminar_producto(venta);
	                break;
	            case 3:
	                ver_detalle_ticket(venta);
	                break;
	            case 4:
	                generar_ticket(venta);
	                break;
	            case 5:
	                System.out.println("Saliendo del punto de venta...");
	                return;
	            default:
	                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
	        }
	    } while (true);
	}

	 public static Ventas agregar_producto(Ventas venta) throws IOException {
	        System.out.println("Ingrese el código del producto que desea agregar: ");
	        String codigo = bf.readLine();
	        System.out.println("Ingrese la cantidad: ");
	        int cantidad = Integer.parseInt(bf.readLine());

	        Productos productoEncontrado = null;
	        for (Productos producto : prod) {
	            if (producto.getId().equals(codigo)) {
	                productoEncontrado = producto;
	                break;
	            }
	        }

	        if (productoEncontrado != null) {
	            venta.agregarProducto(productoEncontrado, cantidad);
	            System.out.println("Producto agregado al ticket.");
	        } else {
	            System.out.println("No se encontró ningún producto con el código proporcionado.");
	        }
	        
	        return venta;
	    }

	    public static Ventas eliminar_producto(Ventas venta) throws IOException {
	        System.out.println("Ingrese el índice del producto que desea eliminar: ");
	        int indice = Integer.parseInt(bf.readLine());

	        venta.eliminarProducto(indice);
	        return venta;
	   
	    } 
	    
	    public static void ver_detalle_ticket(Ventas venta) {
	        System.out.println("\n=== DETALLE DEL TICKET ===");
	        System.out.println("Producto\tCantidad\tPrecio unitario\tSubtotal");
	        System.out.println("-----------------------------------------------");
	        int total = 0;
	        for (Venta_Por_Unidad ventaPorUnidad : venta.getVenta()) {
	            int cantidad = ventaPorUnidad.getCant();
	            int precioUnitario = ventaPorUnidad.getPrecio();
	            int subtotal = ventaPorUnidad.getPrecio_total();
	            total+=subtotal;
	            System.out.print(ventaPorUnidad.getNombre() + "\t" + cantidad + "\t\t$" + precioUnitario + "\t\t$" + subtotal + "\n");
	        }
	        venta.setTotal(total);
	        System.out.println("-----------------------------------------------");
	        System.out.println("TOTAL\t\t\t\t\t$" + venta.getTotal());
	        System.out.println("===============================================\n");
	    }
    
    public static void generar_ticket(Ventas venta) {
    	Date date = new Date();
    	String fecha = date.toString();
    	venta.setFecha(fecha);
    	int total=0;
        try {
            FileWriter archivo = new FileWriter("Ticket.txt", true);
            archivo.write("=== DETALLE DEL TICKET ===\n");
            archivo.write("Fecha: " + venta.getFecha() + "\n");
            archivo.write("------------------------------\n");
            for (Venta_Por_Unidad ventaPorUnidad : venta.getVenta()) {
                archivo.write("Producto: " + ventaPorUnidad.getNombre() + "\n");
                archivo.write("Cantidad: " + ventaPorUnidad.getCant() + "\n");
                archivo.write("Precio unitario: $" + ventaPorUnidad.getPrecio() + "\n");
                archivo.write("Subtotal: $" + ventaPorUnidad.getPrecio_total() + "\n");
                total+=ventaPorUnidad.getPrecio_total();
                archivo.write("------------------------------\n");
            }
            archivo.write("TOTAL: $" + total + "\n");
            archivo.write("==============================\n\n");
            archivo.close();
            System.out.println("Recibo generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al generar el recibo: " + e.getMessage());
        }
    }
}