package Utilidades;

/**
 * Clase utilitaria que contiene las consultas SQL correctas para cada tabla
 * según la estructura de la base de datos
 */
public class ConsultasSql {
    
    /**
     * Consulta para obtener todos los productos
     */
    public static final String PRODUCTOS_SELECT = 
        "SELECT idProducto, producto, precio_venta, stock, descripcion, idcategoria, marca, sku FROM Productos";
    
    /**
     * Consulta para obtener un producto por su ID
     * @param idProducto ID del producto a buscar
     * @return Consulta SQL formateada
     */
    public static String getProductoPorId(String idProducto) {
        return "SELECT idProducto, producto, precio_venta, stock, descripcion, idcategoria, marca, sku " +
               "FROM Productos WHERE idProducto = '" + idProducto + "'";
    }
    
    /**
     * Consulta para obtener categorías
     */
    public static final String CATEGORIAS_SELECT = "SELECT id, nombre FROM Categorias";
    
    /**
     * Consulta para obtener proveedores
     */
    public static final String PROVEEDORES_SELECT = "SELECT idProveedor, nombre FROM Proveedores";
    
    /**
     * Consulta para obtener una venta por su ID
     * @param idVenta ID de la venta a buscar
     * @return Consulta SQL formateada
     */
    public static String getVentaPorId(String idVenta) {
        return "SELECT idVenta, fecha, total, idTipoVenta, cancelada " +
               "FROM Ventas WHERE idVenta = '" + idVenta + "'";
    }
    
    /**
     * Consulta para obtener ventas
     * @param fechaInicio Fecha de inicio en formato YYYY-MM-DD
     * @param fechaFin Fecha de fin en formato YYYY-MM-DD
     * @return Consulta SQL formateada
     */
    public static String getVentasPorFecha(String fechaInicio, String fechaFin) {
        return "SELECT idVenta, fecha, total, idTipoVenta, cancelada " +
               "FROM Ventas WHERE fecha BETWEEN '" + fechaInicio + "' AND '" + 
               fechaFin + "' ORDER BY fecha DESC";
    }
    
    /**
     * Consulta para obtener los detalles de una venta
     * @param idVenta ID de la venta
     * @return Consulta SQL formateada
     */
    public static String getDetalleVenta(String idVenta) {
        return "SELECT d.idProducto, p.producto, d.cantidad, d.precioUnitario, " +
               "(CAST(dv.cantidad AS FLOAT) * CAST(dv.precioUnitario AS FLOAT) * 1.16) as subtotal " +
               "FROM DetalleVentas d JOIN Productos p ON d.idProducto = p.idProducto " +
               "WHERE d.idVenta = '" + idVenta + "'";
    }
    
    /**
     * Consulta para obtener cierres de caja por fecha
     * @param fecha Fecha en formato YYYY-MM-DD
     * @return Consulta SQL formateada
     */
    public static String getCierreCaja(String fecha) {
        return "SELECT id, fecha, hora, monto_inicial, monto_final, total_ventas, " +
               "ventas_efectivo, ventas_tarjeta, ventas_transferencia " +
               "FROM CajaCierres WHERE fecha = '" + fecha + "'";
    }
    
    /**
     * Consulta para reporte detallado de ventas con productos
     * @param fechaInicio Fecha de inicio en formato YYYY-MM-DD
     * @param fechaFin Fecha de fin en formato YYYY-MM-DD
     * @return Consulta SQL formateada
     */
    public static String getReporteVentasDetallado(String fechaInicio, String fechaFin) {
        return "SELECT v.idVenta, v.fecha, p.producto, dv.cantidad, dv.precioUnitario, " +
               "(CAST(dv.cantidad AS FLOAT) * CAST(dv.precioUnitario AS FLOAT) * 1.16) AS subtotal, v.total " +
               "FROM Ventas v " +
               "INNER JOIN DetalleVentas dv ON v.idVenta = dv.idVenta " +
               "INNER JOIN Productos p ON dv.idProducto = p.idProducto " +
               "WHERE v.fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' " +
               "ORDER BY v.fecha DESC, v.idVenta";
    }
        
        /**
         * Consulta para reporte detallado de ventas no canceladas con productos
         * @param fechaInicio Fecha de inicio en formato YYYY-MM-DD
         * @param fechaFin Fecha de fin en formato YYYY-MM-DD
         * @return Consulta SQL formateada
         */
        public static String getReporteVentasNoCanceladasDetallado(String fechaInicio, String fechaFin) {
            return "SELECT v.idVenta, v.fecha, p.producto, dv.cantidad, dv.precioUnitario, " +
                   "(CAST(dv.cantidad AS FLOAT) * CAST(dv.precioUnitario AS FLOAT) * 1.16) AS subtotal, v.total " +
                   "FROM Ventas v " +
                   "INNER JOIN DetalleVentas dv ON v.idVenta = dv.idVenta " +
                   "INNER JOIN Productos p ON dv.idProducto = p.idProducto " +
                   "WHERE v.fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' " +
                   "AND (v.cancelada IS NULL OR v.cancelada != 1) " +
                   "ORDER BY v.fecha DESC, v.idVenta";
        }
        
        /**
         * Consulta para obtener productos más vendidos en un rango de fechas
         * @param fechaInicio Fecha de inicio en formato YYYY-MM-DD
         * @param fechaFin Fecha de fin en formato YYYY-MM-DD
         * @param limite Cantidad máxima de productos a mostrar
         * @return Consulta SQL formateada
         */
        public static String getProductosMasVendidos(String fechaInicio, String fechaFin, int limite) {
            return "SELECT TOP " + limite + " p.idProducto, p.producto, p.precio_venta, " +
                   "SUM(CAST(dv.cantidad AS FLOAT)) AS cantidad_vendida, " +
                   "SUM((CAST(dv.cantidad AS FLOAT) * CAST(dv.precioUnitario AS FLOAT) * 1.16)) AS total_ventas " +
                   "FROM Ventas v " +
                   "INNER JOIN DetalleVentas dv ON dv.idVenta = v.idVenta " +
                   "INNER JOIN Productos p ON p.idProducto = dv.idProducto " +
                   "WHERE v.fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' " +
                   "AND (v.cancelada IS NULL OR v.cancelada != 1) " +
                   "GROUP BY p.idProducto, p.producto, p.precio_venta " +
                   "ORDER BY cantidad_vendida DESC";
        }
}
