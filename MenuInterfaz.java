package Cordiprogramas;
import java.util.*;
import java.io.*;
public class MenuInterfaz {

    private Inventario inventario = new Inventario();
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Eliminar Producto");
            System.out.println("3. Listar Productos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> eliminarProducto();
                case 3 -> inventario.listarProductos();
                case 4 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private void agregarProducto() {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        Producto producto = new Producto(codigo, nombre, precio, cantidad);
        inventario.agregarProducto(producto);
    }

    private void eliminarProducto() {
        System.out.print("Código del producto a eliminar: ");
        String codigo = scanner.nextLine();
        inventario.eliminarProducto(codigo);
    }
}