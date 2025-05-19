package Controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modelo.BaseDatos;
import Vista.VistaCierreCaja;
import Vista.VistaMenu;

/**
 * Controlador para la funcionalidad de cierre de caja
 */
public class ControladorCierreCaja implements ActionListener {

    private VistaCierreCaja vistaCierreCaja;
    private ControladorMenu controladorMenu;
    private double fondoInicial;
    private double totalVentas;
    private String idApertura;
    private double ventasEfectivo;
    private double ventasTarjeta;
    private double ventasTransferencia;
    private VistaMenu padre;
    private DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    
    /**
     * Método para actualizar la tabla resumen con los datos de ventas
     * @param fecha La fecha para filtrar las ventas
     */
    private void actualizarTablaResumen(String fecha) {
        try {
            BaseDatos bd = new BaseDatos();
            
            // Obtener cantidad de ventas en efectivo
            ArrayList<String[]> cantidadEfectivo = bd.consultar(
            	    "ventas", 
            	    "COUNT(*) AS cantidad", 
            	    "fecha = '" + fecha + "' AND idTipoVenta = '1'"
            	);
            int numVentasEfectivo = 0;
            if (cantidadEfectivo != null && !cantidadEfectivo.isEmpty() && cantidadEfectivo.get(0)[0] != null) {
                numVentasEfectivo = Integer.parseInt(cantidadEfectivo.get(0)[0]);
            }
            
            // Obtener cantidad de ventas con tarjeta
            ArrayList<String[]> cantidadTarjeta = bd.consultar("ventas", "COUNT(*) as cantidad", "fecha = '" + fecha + "' AND idTipoVenta = 2");
            int numVentasTarjeta = 0;
            if (cantidadTarjeta != null && !cantidadTarjeta.isEmpty() && cantidadTarjeta.get(0)[0] != null) {
                numVentasTarjeta = Integer.parseInt(cantidadTarjeta.get(0)[0]);
            }
            
            // Obtener la tabla resumen y actualizar sus valores
            JTable tabla = vistaCierreCaja.getTablaResumen();
            
            // Actualizar datos de ventas en efectivo
            tabla.setValueAt(String.valueOf(numVentasEfectivo), 0, 1); // Cantidad
            tabla.setValueAt("$" + formatoMoneda.format(ventasEfectivo), 0, 2); // Total
            
            // Actualizar datos de ventas con tarjeta
            tabla.setValueAt(String.valueOf(numVentasTarjeta), 1, 1); // Cantidad
            tabla.setValueAt("$" + formatoMoneda.format(ventasTarjeta), 1, 2); // Total
            
            bd.cerrarConexion();
            
        } catch (Exception e) {
            System.err.println("Error al actualizar tabla resumen: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Constructor que inicializa la vista de cierre de caja
     * @param controladorMenu Referencia al controlador del menú principal
     */
    /**
     * Constructor que inicializa la vista de cierre de caja
     * @param controladorMenu Referencia al controlador del menú principal
     */
    public ControladorCierreCaja(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.padre = controladorMenu.getVentana();
        this.vistaCierreCaja = new VistaCierreCaja();
        
        // Configurar listeners
        this.vistaCierreCaja.getBtnCerrarCaja().addActionListener(this);
        this.vistaCierreCaja.getBtnCancelar().addActionListener(this);
        // El botón de imprimir ha sido eliminado
        
        // Añadir listener para el campo de efectivo
        this.vistaCierreCaja.getTEfectivo().addActionListener(this);
        
        // Obtener fecha actual
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formatoFecha.format(fechaActual);
        
        // Inicializar BaseDatos y obtener ventas por tipo de pago
        BaseDatos bd = new BaseDatos();
        ventasEfectivo = bd.obtenerVentasPorTipoPago(fecha, 1); // Suponiendo que 1 es el código para ventas en efectivo
        ventasTarjeta = bd.obtenerVentasPorTipoPago(fecha, 2); // Suponiendo que 2 es el código para ventas con tarjeta
        ventasTransferencia = bd.obtenerVentasPorTipoPago(fecha, 3); // Suponiendo que 3 es el código para ventas por transferencia
        
        // Establecer el nombre del cajero actual
        this.vistaCierreCaja.setCajero(controladorMenu.getNombreUsuarioActual());
        
        // Cargar datos iniciales
        cargarDatosIniciales();
        
        // Calcular ganancia (asumiendo que es la suma total de ventas)
        double ganancia = totalVentas; // 20% de ganancia, ajustar según tu lógica de negocio
        if (vistaCierreCaja.getTGanancias() != null) {
            vistaCierreCaja.getTGanancias().setText(formatoMoneda.format(ganancia));
        }
        
        // Mostrar la vista de cierre de caja
        controladorMenu.getVentana().getEscritorio().add(this.vistaCierreCaja);
        this.vistaCierreCaja.setVisible(true);
    }
    
    /**
     * Devuelve la ventana de cierre de caja
     * @return JInternalFrame que contiene la vista
     */
    public JInternalFrame getVentana() {
        return this.vistaCierreCaja;
    }
    
    /**
     * Carga datos iniciales para el cierre de caja
     */
    private void cargarDatosIniciales() {
        try {
            // Obtener la apertura de caja más reciente que esté abierta
            BaseDatos bd = new BaseDatos();
            
            // Obtener datos de la apertura de caja
            ArrayList<String[]> aperturas = bd.consultar("CajaAperturas", "TOP 1 *", "estado='1' ORDER BY id DESC");
            
            if (aperturas != null && !aperturas.isEmpty()) {
                String[] apertura = aperturas.get(0);
                idApertura = apertura[0]; // ID ahora es string
                fondoInicial = Double.parseDouble(apertura[3]);
                String fecha = apertura[1];
                String hora = apertura[2];
                
                // Mostrar información de la apertura
                vistaCierreCaja.getLFecha().setText(fecha);
                vistaCierreCaja.getLHora().setText(hora);
                vistaCierreCaja.getTFondoInicial().setText("$" + formatoMoneda.format(fondoInicial));
                
                // Reiniciar los valores
                ventasEfectivo = 0;
                ventasTarjeta = 0;
                ventasTransferencia = 0;
                totalVentas = 0;
                
                // Usar el nuevo método para obtener directamente el total de ventas del día
                totalVentas = bd.obtenerTotalVentasDia(fecha);
                System.out.println("Total de ventas obtenido directamente: $" + totalVentas);
                
                // Debugging: Imprimir la fecha que estamos consultando
                System.out.println("Consultando ventas para la fecha: " + fecha);
                
                // Verificar todas las ventas de ese día (para depuración)
                ArrayList<String[]> todasLasVentas = bd.consultar(
                        "Ventas", "*", "fecha='" + fecha + "'");
                System.out.println("Total de ventas encontradas: " + (todasLasVentas != null ? todasLasVentas.size() : 0));
                
                // Obtener ventas en efectivo (tipo pago 1)
                ArrayList<String[]> ventasEfectivoQuery = bd.consultar(
                        "Ventas", "SUM(CAST(total AS FLOAT)) AS total_ventas", "CONVERT(DATE, fecha) = '" + fecha + "' AND idTipoVenta='1'");
                
                if (ventasEfectivoQuery != null && !ventasEfectivoQuery.isEmpty() && ventasEfectivoQuery.get(0)[0] != null) {
                    ventasEfectivo = Double.parseDouble(ventasEfectivoQuery.get(0)[0]);
                    System.out.println("Ventas en efectivo: " + ventasEfectivo);
                }
                
                // Obtener ventas con tarjeta (tipo pago 2)
                ArrayList<String[]> ventasTarjetaQuery = bd.consultar(
                		"Ventas", "SUM(CAST(total AS FLOAT)) AS total_ventas", "CONVERT(DATE, fecha) = '" + fecha + "' AND idTipoVenta='2'");
                
                if (ventasTarjetaQuery != null && !ventasTarjetaQuery.isEmpty() && ventasTarjetaQuery.get(0)[0] != null) {
                    ventasTarjeta = Double.parseDouble(ventasTarjetaQuery.get(0)[0]);
                    System.out.println("Ventas con tarjeta: " + ventasTarjeta);
                }
                
                // Obtener ventas por transferencia (tipo pago 3)
                ArrayList<String[]> ventasTransferenciaQuery = bd.consultar(
                        "Ventas", "SUM(CAST(total AS FLOAT)) AS total_ventas", "fecha='" + fecha + "' AND idTipoVenta='3'");
                
                if (ventasTransferenciaQuery != null && !ventasTransferenciaQuery.isEmpty() && ventasTransferenciaQuery.get(0)[0] != null) {
                    ventasTransferencia = Double.parseDouble(ventasTransferenciaQuery.get(0)[0]);
                    System.out.println("Ventas por transferencia: " + ventasTransferencia);
                }
                
                // Calcular el total de ventas
                totalVentas = ventasEfectivo + ventasTarjeta + ventasTransferencia;
                System.out.println("Total de ventas calculado: " + totalVentas);
                
                // Mostrar todas las ventas en el total (sin el símbolo $ para evitar problemas)
                vistaCierreCaja.getTVentasTotal().setText(formatoMoneda.format(totalVentas));
                vistaCierreCaja.getTTotalVentas().setText(formatoMoneda.format(totalVentas));
                vistaCierreCaja.getTTarjeta().setText(formatoMoneda.format(ventasTarjeta + ventasTransferencia));
                vistaCierreCaja.getTTotalVentas().setText(formatoMoneda.format(totalVentas));
                
                // Calcular el total que debería haber en caja (fondo inicial + ventas en efectivo)
                double totalEsperado = fondoInicial + ventasEfectivo; // Solo se considera el efectivo para la caja física
                vistaCierreCaja.getTTotal().setText("$" + formatoMoneda.format(totalEsperado));
                
                // Mostrar las ventas del día en la tabla
                cargarVentasDelDia(fecha);
                
                // Mostrar desglose de ventas por tipo de pago en la consola para depuración
                System.out.println("=== Resumen de ventas del día ===");
                System.out.println("Ventas en efectivo: $" + formatoMoneda.format(ventasEfectivo));
                System.out.println("Ventas con tarjeta: $" + formatoMoneda.format(ventasTarjeta));
                System.out.println("Ventas por transferencia: $" + formatoMoneda.format(ventasTransferencia));
                System.out.println("Total de ventas: $" + formatoMoneda.format(totalVentas));
                
                // Actualizar el campo de dinero en efectivo (solo ventas efectivo, sin fondo inicial)
                vistaCierreCaja.getTDineroEfectivo().setText(formatoMoneda.format(ventasEfectivo));
            } else {
                JOptionPane.showMessageDialog(vistaCierreCaja, 
                        "No hay una caja abierta en este momento.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                vistaCierreCaja.dispose();
            }
            
            bd.cerrarConexion();
        } catch (Exception e) {
            String mensaje = "Error al cargar los datos iniciales: " + e.getMessage();
            
            // Verificar si el error está relacionado con la sintaxis SQL
            if (e.getMessage() != null && e.getMessage().contains("syntax")) {
                mensaje += "\n\nPosible error de sintaxis SQL. Consulte al administrador del sistema.";
            }
            
            JOptionPane.showMessageDialog(vistaCierreCaja, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar datos iniciales: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Carga las ventas del día en la tabla
     */
    private void cargarVentasDelDia(String fecha) {
        try {
            BaseDatos bd = new BaseDatos();
            
            // Mostrar solo las ventas en efectivo (sin incluir el fondo inicial)
            vistaCierreCaja.getTDineroEfectivo().setText(formatoMoneda.format(ventasEfectivo));
            
            // Modificamos la consulta para considerar solo la parte de fecha
            ArrayList<String[]> ventas = bd.consultar("Ventas v INNER JOIN TipoVenta tv ON v.idTipoVenta = tv.id", 
                    "v.idVenta, v.fecha, v.total, tv.etiqueta as tipo_pago", 
                    "CONVERT(DATE, v.fecha) = '" + fecha + "'");
            
            System.out.println("Consulta de ventas - CONVERT(DATE, v.fecha) = '" + fecha + "'");
            System.out.println("Ventas encontradas: " + (ventas != null ? ventas.size() : 0));
            
            // Verificación adicional: listar todas las ventas para depuración
            ArrayList<String[]> todasVentas = bd.consultar("Ventas", "idVenta, fecha, total", null);
            System.out.println("===== TODAS LAS VENTAS =====");
            if (todasVentas != null) {
                for (String[] v : todasVentas) {
                    System.out.println("ID: " + v[0] + ", Fecha: " + v[1] + ", Total: " + v[2]);
                }
            }
            System.out.println("=========================");
            
            // Crear modelo para la tabla
            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[] {"ID", "Fecha/Hora", "Total", "Tipo de Pago"}, 0);
            
            if (ventas != null && !ventas.isEmpty()) {
              
                
                for (String[] venta : ventas) {
                    double total = Double.parseDouble(venta[2]);
                   
                    
                    // Actualizar totales por tipo de pago
                    String tipoPago = venta[3];
                    if (tipoPago.equalsIgnoreCase("Efectivo")) {
                        ventasEfectivo += total;
                    } else if (tipoPago.equalsIgnoreCase("Tarjeta")) {
                        ventasTarjeta += total;
                    } else if (tipoPago.equalsIgnoreCase("Transferencia")) {
                        ventasTransferencia += total;
                    }
                    
                    modelo.addRow(new Object[] {
                            venta[0],
                            venta[1],
                            "$" + formatoMoneda.format(total),
                            venta[3]
                    });
                }
                
                // Actualizar los totales en la interfaz
                vistaCierreCaja.getTVentasEfectivo().setText("$" + formatoMoneda.format(ventasEfectivo));
                vistaCierreCaja.getTVentasTarjeta().setText("$" + formatoMoneda.format(ventasTarjeta));
                vistaCierreCaja.getTVentasTransferencia().setText("$" + formatoMoneda.format(ventasTransferencia));
                vistaCierreCaja.getTVentasTotal().setText("$" + formatoMoneda.format(totalVentas));
                
                System.out.println("=== Resumen de ventas del día ===");
                System.out.println("Ventas en efectivo: $" + formatoMoneda.format(ventasEfectivo));
                System.out.println("Ventas con tarjeta: $" + formatoMoneda.format(ventasTarjeta));
                System.out.println("Ventas por transferencia: $" + formatoMoneda.format(ventasTransferencia));
                System.out.println("Total de ventas: $" + formatoMoneda.format(totalVentas));
            } else {
                System.out.println("No se encontraron ventas para la fecha: " + fecha);
                // Reiniciar totales en caso de no haber ventas
                totalVentas = 0.0;
                ventasEfectivo = 0.0;
                ventasTarjeta = 0.0;
                ventasTransferencia = 0.0;
                
                vistaCierreCaja.getTVentasEfectivo().setText("$0.00");
                vistaCierreCaja.getTVentasTarjeta().setText("$0.00");
                vistaCierreCaja.getTVentasTransferencia().setText("$0.00");
                vistaCierreCaja.getTVentasTotal().setText("$0.00");
            }
            
            vistaCierreCaja.getTablaResumen().setModel(modelo);
            bd.cerrarConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaCierreCaja, 
                    "Error al cargar las ventas del día: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Calcula la diferencia entre el monto físico y el esperado
     */
    private void calcularDiferencia() {
        try {
            // Obtener el monto en efectivo ingresado por el usuario
            String montoTexto = vistaCierreCaja.getTEfectivo().getText().trim().replace(",", "");
            
            if (montoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vistaCierreCaja, 
                        "Por favor ingrese el monto físico en caja.", 
                        "Campo Requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double montoFisico = Double.parseDouble(montoTexto);
            // Calcular el efectivo esperado (fondo inicial + ventas en efectivo)
            double efectivoEsperado = fondoInicial + ventasEfectivo;
            double diferencia = montoFisico - efectivoEsperado;
            
            // Mostrar la diferencia formateada
            vistaCierreCaja.getTDiferencia().setText("$" + formatoMoneda.format(diferencia));
            
            // Cambiar el color según si hay faltante o sobrante
            if (diferencia < 0) {
                // Cambiar el color a rojo para faltante
                vistaCierreCaja.getTDiferencia().setForeground(new Color(231, 76, 60));
            } else if (diferencia > 0) {
                // Cambiar el color a verde para sobrante
                vistaCierreCaja.getTDiferencia().setForeground(new Color(46, 204, 113));
            } else {
                // Negro si no hay diferencia
                vistaCierreCaja.getTDiferencia().setForeground(Color.BLACK);
            }
            
            // Actualizar el total en caja (efectivo ingresado)
            vistaCierreCaja.getTTotal().setText("$" + formatoMoneda.format(montoFisico));
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaCierreCaja, 
                    "Por favor ingrese un monto válido (sólo números y punto decimal).", 
                    "Formato Inválido", JOptionPane.WARNING_MESSAGE);
            vistaCierreCaja.getTDiferencia().setText("Error");
            vistaCierreCaja.getTDiferencia().setForeground(Color.RED);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaCierreCaja, 
                    "Error al calcular la diferencia: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Realiza el cierre de caja
     */
    private void cerrarCaja() {
        try {
            // Asegurarnos de que el total de ventas esté actualizado
            System.out.println("Al cerrar caja - Total de ventas: " + totalVentas);
            System.out.println("Al cerrar caja - Ventas en efectivo: " + ventasEfectivo);
            System.out.println("Al cerrar caja - Ventas con tarjeta: " + ventasTarjeta);
            System.out.println("Al cerrar caja - Ventas por transferencia: " + ventasTransferencia);
            System.out.println("Al cerrar caja - Dinero en efectivo (fondo inicial + ventas efectivo): " + (fondoInicial + ventasEfectivo));
            
            String montoTexto = vistaCierreCaja.getTEfectivo().getText().trim().replace(",", "");
            
            if (montoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vistaCierreCaja, 
                        "Por favor ingrese el monto físico en caja.", 
                        "Campo Requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double montoFisico = Double.parseDouble(montoTexto);
            double totalEsperado = fondoInicial + ventasEfectivo; // Solo se suma el efectivo para la caja física
            double diferencia = montoFisico - totalEsperado;
            
            // Obtener fecha y hora actual
            Date fechaActual = new Date();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            String fecha = formatoFecha.format(fechaActual);
            String hora = formatoHora.format(fechaActual);
            
            // Generar ID único para el cierre (usando timestamp)
            String id = String.valueOf(System.currentTimeMillis());
            
            // Registrar cierre de caja
            BaseDatos bd = new BaseDatos();
            String[] valores = {
                id,
                fecha,
                hora,
                String.valueOf(fondoInicial),
                String.valueOf(totalEsperado),
                String.valueOf(ventasEfectivo),
                String.valueOf(ventasTarjeta),
                String.valueOf(ventasTransferencia),
                String.valueOf(totalVentas),
             // Diferencia calculada
                String.valueOf(montoFisico),
               
                controladorMenu.getNombreUsuarioActual() // Usuario actual
            };
            
            boolean resultado = bd.insertar("CajaCierres", 
            	    "id,fecha,hora,monto_inicial,monto_final,ventas_efectivo,ventas_tarjeta,ventas_transferencia,total_ventas,monto_fisico,id_usuario", 
            	    valores);
            if (resultado) {
                // Actualizar el estado de la apertura de caja
                bd.modificar("CajaAperturas", "estado", "0", "id='" + idApertura + "'");
                
                // Registrar hora de cierre y monto final en CajaAperturas
                bd.modificar("CajaAperturas", "monto_final", String.valueOf(totalEsperado), "id='" + idApertura + "'");
                bd.modificar("CajaAperturas", "hora_cierre", hora, "id='" + idApertura + "'");
                
                // Actualizar el estado de la caja en el controlador de menú
                controladorMenu.actualizarEstadoCaja(false);
                
                JOptionPane.showMessageDialog(vistaCierreCaja, 
                        "Cierre de caja realizado con éxito.", 
                        "Cierre Exitoso", JOptionPane.INFORMATION_MESSAGE);
                
                vistaCierreCaja.dispose();
            } else {
                JOptionPane.showMessageDialog(vistaCierreCaja, 
                        "Error al registrar el cierre de caja. Intente nuevamente.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            bd.cerrarConexion();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaCierreCaja, 
                    "Por favor ingrese un monto válido (sólo números y punto decimal).", 
                    "Formato Inválido", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaCierreCaja, 
                    "Error al cerrar la caja: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
        
        /**
         * Devuelve la vista de cierre de caja
         * @return Vista de cierre de caja
         */
        public VistaCierreCaja getVistaCierreCaja() {
            return this.vistaCierreCaja;
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCierreCaja.getTEfectivo()) {
            // Cada vez que se modifica el valor del efectivo, recalcular la diferencia
            calcularDiferencia();
        } else if (e.getSource() == vistaCierreCaja.getBtnCerrarCaja()) {
            // Lógica para cerrar la caja
            cerrarCaja();
        } else if (e.getSource() == vistaCierreCaja.getBtnCancelar()) {
            // Cerrar la ventana sin guardar cambios
            vistaCierreCaja.dispose();
        } else if (e.getSource() == vistaCierreCaja.getBtnImprimir()) {
            // Lógica para imprimir el reporte de cierre
            JOptionPane.showMessageDialog(vistaCierreCaja, 
                "Imprimiendo reporte de cierre de caja...",
                "Impresión", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
