package Thelastdance;

import java.awt.EventQueue;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Modelo.BaseDatos;
import Modelo.Conexion;
import Modelo.Usuario;
import Vista.LoginView;

import java.awt.EventQueue;

public class PuntoDeVentaMVC {

    public static void main(String[] args) {
        try {
            // Verificar conexión a la base de datos
            Conexion conexion = new Conexion();
            if (conexion.obtenerConexion() == null) {
                JOptionPane.showMessageDialog(null, 
                        "Error de conexión a la base de datos.\nVerifique la configuración del servidor.", 
                        "Error de Conexión", 
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            
            // Verificar si existe la tabla Usuarios
            BaseDatos bd = new BaseDatos();
            comprobarYCrearTablaUsuarios(bd);
            comprobarYCrearTablaCajaAperturas(bd);
            
            // Verificar si hay usuarios en la base de datos
            ArrayList<String[]> usuarios = bd.consultar("Usuarios", "COUNT(*) as total", null);
            
            if (usuarios != null && usuarios.size() > 0) {
                String totalUsuarios = usuarios.get(0)[0];
                
                // Si no hay usuarios, crear usuarios por defecto
                if (totalUsuarios.equals("0")) {
                    crearUsuariosIniciales();
                    System.out.println("✅ Se han creado usuarios iniciales por defecto.");
                    JOptionPane.showMessageDialog(null, 
                            "Se han creado usuarios por defecto:\n" +
                            "- Usuario: admin, Contraseña: admin (Administrador)\n" +
                            "- Usuario: vendedor, Contraseña: 123 (Vendedor)",
                            "Usuarios Creados", 
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                crearUsuariosIniciales();
                System.out.println("✅ Se han creado usuarios iniciales por defecto.");
            }
            
            bd.cerrarConexion();
            
            // Iniciar la aplicación
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        LoginView frame = new LoginView();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
                
                /**
                 * Comprueba si existe la tabla CajaAperturas y la crea si no existe
                 * @param bd Objeto BaseDatos para realizar consultas
                 */
                private static void verificarYAgregarCamposActivo(BaseDatos bd) {
                    try {
                        // Verificar si el campo activo existe en la tabla Productos
                        ArrayList<String[]> columnaProductos = bd.consultar("INFORMATION_SCHEMA.COLUMNS", "COLUMN_NAME", 
                                "TABLE_NAME = 'Productos' AND COLUMN_NAME = 'activo'");
                        
                        if (columnaProductos == null || columnaProductos.isEmpty()) {
                            System.out.println("ℹ️ Agregando campo 'activo' a la tabla Productos...");
                            bd.ejecutarSQL("ALTER TABLE Productos ADD activo BIT DEFAULT 1");
                        }
                        
                        // Verificar si el campo activo existe en la tabla Categorias
                        ArrayList<String[]> columnaCategorias = bd.consultar("INFORMATION_SCHEMA.COLUMNS", "COLUMN_NAME", 
                                "TABLE_NAME = 'Categorias' AND COLUMN_NAME = 'activo'");
                        
                        if (columnaCategorias == null || columnaCategorias.isEmpty()) {
                            System.out.println("ℹ️ Agregando campo 'activo' a la tabla Categorias...");
                            bd.ejecutarSQL("ALTER TABLE Categorias ADD activo BIT DEFAULT 1");
                        }
                        
                        // Verificar si el campo activo existe en la tabla Proveedores
                        ArrayList<String[]> columnaProveedores = bd.consultar("INFORMATION_SCHEMA.COLUMNS", "COLUMN_NAME", 
                                "TABLE_NAME = 'Proveedores' AND COLUMN_NAME = 'activo'");
                        
                        if (columnaProveedores == null || columnaProveedores.isEmpty()) {
                            System.out.println("ℹ️ Agregando campo 'activo' a la tabla Proveedores...");
                            bd.ejecutarSQL("ALTER TABLE Proveedores ADD activo BIT DEFAULT 1");
                        }
                        
                    } catch (Exception e) {
                        System.err.println("❌ Error al verificar o agregar campos activo: " + e.getMessage());
                    }
                }
                
                private static void comprobarYCrearTablaCajaAperturas(BaseDatos bd) {
                    try {
            // Verificar si existe la tabla CajaAperturas
            ArrayList<String[]> tablas = bd.consultar("INFORMATION_SCHEMA.TABLES", "TABLE_NAME", 
                    "TABLE_TYPE = 'BASE TABLE' AND TABLE_NAME = 'CajaAperturas'");
            
            if (tablas == null || tablas.isEmpty()) {
                // La tabla no existe, crearla
                Statement stmt = Conexion.obtenerConexion().createStatement();
                
                String sql = "CREATE TABLE CajaAperturas ("
                        + "id INT IDENTITY(1,1) PRIMARY KEY,"
                        + "fecha DATE NOT NULL,"
                        + "hora TIME NOT NULL,"
                        + "monto_inicial DECIMAL(10, 2) NOT NULL,"
                        + "estado INT NOT NULL,"  // 1 = Abierta, 0 = Cerrada
                        + "monto_final DECIMAL(10, 2) NULL,"
                        + "fecha_cierre DATE NULL,"
                        + "hora_cierre TIME NULL"
                        + ")";
                
                stmt.executeUpdate(sql);
                System.out.println("Tabla CajaAperturas creada con éxito.");
                
                // Crear índice para mejorar búsquedas por estado
                String sqlIndice = "CREATE INDEX IX_CajaAperturas_Estado ON CajaAperturas (estado)";
                stmt.executeUpdate(sqlIndice);
            }
                    } catch (Exception e) {
            System.err.println("Error al comprobar/crear tabla CajaAperturas: " + e.getMessage());
            e.printStackTrace();
                    }
                }
    
    private static void comprobarYCrearTablaUsuarios(BaseDatos bd) {
        try {
            // Intentamos consultar para ver si la tabla existe
            bd.consultar("Usuarios", "1", "1=0");
        } catch (Exception e) {
            // Si hay error, creamos la tabla
            System.out.println("⚠️ La tabla Usuarios no existe. Creándola...");
            
            try {
                String sql = "CREATE TABLE Usuarios (" +
                             "usuario VARCHAR(50) PRIMARY KEY, " +
                             "contraseña VARCHAR(50) NOT NULL, " +
                             "rol VARCHAR(20) NOT NULL" +
                             ")";
                
                // Verificar y agregar campos de activo a las tablas si no existen
                verificarYAgregarCamposActivo(bd);
                
                // Aquí podrías ejecutar esta sentencia SQL si tuvieras un método para ello
                // Por ahora lo mostramos en la consola
                System.out.println("Ejecutando SQL: " + sql);
            } catch (Exception ex) {
                System.err.println("❌ Error al crear tabla Usuarios: " + ex.getMessage());
            }
        }
    }
    
    private static void crearUsuariosIniciales() {
        // Crear usuario administrador
        Usuario admin = new Usuario("admin", "admin", "administrador");
        admin.guardarEnBD();
        
        // Crear usuario vendedor
        Usuario vendedor = new Usuario("vendedor", "123", "vendedor");
        vendedor.guardarEnBD();
    }
}
