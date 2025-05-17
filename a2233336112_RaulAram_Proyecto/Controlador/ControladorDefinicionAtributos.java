package Controlador;

import Modelo.DefinicionAtributo;
import Vista.VistaDefinicionAtributos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la definición de atributos de entidades
 */


import Modelo.DefinicionAtributo;
import Vista.VistaDefinicionAtributos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la definición de atributos de entidades
 */
public class ControladorDefinicionAtributos {
    private VistaDefinicionAtributos vista;
    private List<DefinicionAtributo> atributosDefinidos;
    
    /**
     * Constructor que inicializa el controlador
     */
    public ControladorDefinicionAtributos() {
        vista = new VistaDefinicionAtributos();
        
        // Configurar listeners
        vista.setAgregarAtributoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.agregarAtributo();
            }
        });
        
        vista.setEliminarAtributoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.eliminarAtributo();
            }
        });
        
        vista.setTerminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminarDefinicion();
            }
        });
        
        // Mostrar la vista
        vista.setVisible(true);
    }
    
    /**
     * Método que se ejecuta al finalizar la definición de atributos
     */
    private void terminarDefinicion() {
        // Obtener los atributos definidos
        atributosDefinidos = vista.getDefiniciones();
        
        // Verificar que haya al menos un atributo definido
        if (atributosDefinidos.isEmpty()) {
            vista.mostrarMensaje("Debe definir al menos un atributo para la entidad");
            return;
        }
        
        // Cerrar la vista actual
        vista.dispose();
    }
    
    /**
     * Obtiene la vista asociada al controlador
     * @return Vista de definición de atributos
     */
    public VistaDefinicionAtributos getVista() {
        return vista;
    }
    
    /**
     * Obtiene los atributos definidos
     * @return Lista de atributos definidos
     */
    public List<DefinicionAtributo> getAtributosDefinidos() {
        return atributosDefinidos;
    }
}
