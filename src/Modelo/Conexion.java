package Modelo;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
	private static final String URL = "jdbc:sqlserver://localhost:14330;databaseName=Proyecto;user=raul;password=12345;encrypt=false;trustServerCertificate=true;";

    public static Connection conexion = null;

    public static Connection obtenerConexion() {
        try {
            // Si la conexión es nula o está cerrada, crear una nueva
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conexion = DriverManager.getConnection(URL);
                System.out.println("✅ Conexión exitosa a la base de datos.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error al cargar el controlador JDBC de SQL Server: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
    
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    // Agrega este método solo para probar la conexión directamente desde esta clase
    public static void main(String[] args) {
        obtenerConexion();
        cerrarConexion();
    }
}