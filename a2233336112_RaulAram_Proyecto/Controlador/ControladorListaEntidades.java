package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import Vista.VistaListaEntidades;
import dao.EntidadDAO;


import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import Vista.VistaListaEntidades;
import dao.EntidadDAO;

/**
 * Controlador para gestionar la vista de lista de entidades
 */
public class ControladorListaEntidades {
    private VistaListaEntidades vista;
    private List<String> nombresAtributos;
    private List<Map<String, Object>> entidades;
    private EntidadDAO dao;
    private ActionListener onVolverListener;

    /**
     * Constructor del controlador de lista de entidades
     * @param nombresAtributos Lista de nombres de los atributos
     * @param entidades Lista de entidades a mostrar
     * @param dao DAO para operaciones con entidades
     */
    public ControladorListaEntidades(List<String> nombresAtributos, List<Map<String, Object>> entidades, EntidadDAO dao) {
        this.nombresAtributos = nombresAtributos;
        this.entidades = entidades;
        this.dao = dao;

        vista = new VistaListaEntidades(nombresAtributos, entidades);
        agregarEventos();
        vista.setVisible(true);
    }
    
    /**
     * Agrega los eventos a los botones de la vista
     */
    private void agregarEventos() {
        vista.getBtnEliminar().addActionListener(e -> {
            int fila = vista.getTabla().getSelectedRow();
            if (fila >= 0) {
                // Verificar que la fila sea válida
                if (fila < entidades.size()) {
                    if (vista.confirmarAccion("¿Está seguro de eliminar esta entidad?")) {
                        entidades.remove(fila);
                        dao.guardar(entidades);
                        vista.actualizarTabla(entidades);
                        vista.mostrarMensaje("Entidad eliminada correctamente");
                    }
                } else {
                    vista.mostrarMensaje("Error: La fila seleccionada no existe en los datos.");
                }
            } else {
                vista.mostrarMensaje("Selecciona una fila para eliminar.");
            }
        });

        vista.getBtnModificar().addActionListener(e -> {
            int fila = vista.getTabla().getSelectedRow();
            if (fila >= 0) {
                // Verificar que la fila sea válida
                if (fila < entidades.size()) {
                    Map<String, Object> entidad = entidades.get(fila);
                    Map<String, Object> nuevaEntidad = vista.editarFila(fila);
                    if (nuevaEntidad != null) {
                        entidades.set(fila, nuevaEntidad);
                        dao.guardar(entidades);
                        vista.actualizarTabla(entidades);
                        vista.mostrarMensaje("Entidad modificada correctamente");
                    }
                } else {
                    vista.mostrarMensaje("Error: La fila seleccionada no existe en los datos.");
                }
            } else {
                vista.mostrarMensaje("Selecciona una fila para modificar.");
            }
        });

        vista.getBtnAgregar().addActionListener(e -> {
            Map<String, Object> nueva = vista.nuevaFila();
            if (nueva != null) {
                entidades.add(nueva);
                dao.guardar(entidades);
                vista.actualizarTabla(entidades);
                vista.mostrarMensaje("Entidad agregada correctamente");
            }
        });
        
        vista.getBtnVolver().addActionListener(e -> {
            vista.dispose();
            if (onVolverListener != null) {
                onVolverListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "volver"));
            }
        });
    }
    
    /**
     * Configura un listener para ser notificado cuando se cierra la vista
     * @param listener ActionListener a invocar cuando se cierra
     */
    public void setOnVolverListener(ActionListener listener) {
        this.onVolverListener = listener;
    }
    
    /**
     * Método para confirmar acciones
     * @param mensaje Mensaje a mostrar
     * @return true si se confirma, false en caso contrario
     */
    public boolean confirmarAccion(String mensaje) {
        return vista.confirmarAccion(mensaje);
    }
}