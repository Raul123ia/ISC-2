package Main;

import Controlador.ControladorDefinicionAtributos;
import Controlador.ControladorEntidad;

import Vista.VistaDefinicionAtributos;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

import dao.EntidadDAO;

public class Main {
    // Variable para controlar si ya se ha abierto una ventana de entidad
    private static boolean ventanaEntidadAbierta = false;
    
    public static void main(String[] args) {
        // Configurar un hook de cierre para guardar los datos cuando se cierre la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Aplicación cerrándose. Guardando datos...");
            try {
                // Crear una instancia del DAO y guardar las entidades
                EntidadDAO dao = new EntidadDAO();
                List<Map<String, Object>> entidades = dao.obtenerEntidades();
                if (entidades != null && !entidades.isEmpty()) {
                    dao.guardar(entidades);
                    System.out.println("Datos guardados exitosamente: " + entidades.size() + " entidades");
                } else {
                    System.out.println("No hay datos para guardar");
                }
            } catch (Exception e) {
                System.err.println("Error al guardar datos durante el cierre: " + e.getMessage());
                e.printStackTrace();
            }
        }));
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ControladorDefinicionAtributos ctrlDef = new ControladorDefinicionAtributos();

                ctrlDef.getVista().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Solo crear una nueva ventana de entidad si no hay otra abierta
                        if (!ventanaEntidadAbierta) {
                            ventanaEntidadAbierta = true;
                            ControladorEntidad controlador = new ControladorEntidad(ctrlDef.getAtributosDefinidos());
                            
                            // Cuando se cierre la ventana de entidad, marcar que ya no está abierta
                            if (controlador.getVista() != null) {
                                controlador.getVista().addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        ventanaEntidadAbierta = false;
                                    }
                                });
                            }
                        } else {
                            System.out.println("Ya hay una ventana de entidad abierta. No se abrirá otra.");
                        }
                    }
                });
            }
        });
    }
}