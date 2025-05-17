package Controlador;

import Modelo.DefinicionAtributo;
import Modelo.Entidad;
import Vista.VistaEntidad;
import Vista.VistaListaEntidades;
import dao.EntidadDAO;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class ControladorEntidad {
    private VistaEntidad vista;
    private List<DefinicionAtributo> definiciones;
    private List<Map<String, Object>> listaEntidades;
    private EntidadDAO dao;
    private VistaListaEntidades vistaLista;
    
    public ControladorEntidad(List<DefinicionAtributo> definiciones) {
        this.definiciones = definiciones;
        this.vista = new VistaEntidad();
        this.dao = new EntidadDAO();
        
        // Verificar que tenemos definiciones
        if (this.definiciones == null || this.definiciones.isEmpty()) {
            System.err.println("ADVERTENCIA: No hay definiciones de atributos disponibles");
            this.definiciones = new ArrayList<>();
        } else {
            System.out.println("Definiciones de atributos cargadas: " + this.definiciones.size());
            // Imprimir los nombres de los atributos para verificar
            for (DefinicionAtributo def : this.definiciones) {
                System.out.println("  - Atributo: " + def.getNombre() + " (Tipo: " + def.getTipo() + ")");
            }
        }
        
        // Cargar entidades existentes del archivo JSON
        try {
            this.listaEntidades = dao.cargar();
            if (this.listaEntidades == null) {
                this.listaEntidades = new ArrayList<>();
            }
            System.out.println("Se cargaron " + listaEntidades.size() + " entidades del archivo JSON");
            
            // Imprimir el contenido de las entidades
            for (int i = 0; i < listaEntidades.size(); i++) {
                System.out.println("Entidad " + (i+1) + ": " + listaEntidades.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar entidades: " + e.getMessage());
            this.listaEntidades = new ArrayList<>();
        }
        
        inicializarCampos();
        agregarEventos();
        vista.setVisible(true);
    }
    
    private void inicializarCampos() {
        for (DefinicionAtributo attr : definiciones) {
            vista.agregarCampo(attr.getNombre(), attr.getTipo());
        }
    }
    
    private void agregarEventos() {
        vista.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener valores de la vista
                    Map<String, Object> valores = vista.obtenerValores();
                    
                    // Verificar que se hayan obtenido valores
                    if (valores.isEmpty()) {
                        vista.mostrarMensaje("Error: No se pudieron obtener los valores de los campos");
                        return;
                    }
                    
                    // Crear objeto entidad para mostrar información
                    Entidad entidad = new Entidad(valores);
                    
                    // Agregar a la lista de entidades
                    listaEntidades.add(valores);
                    
                    // Mostrar información de depuración
                    System.out.println("Guardando " + listaEntidades.size() + " entidades en el archivo JSON");
                    System.out.println("Nueva entidad: " + valores);
                    
                    // Guardar en el archivo JSON
                    dao.guardar(listaEntidades);
                    
                    // Actualizar la vista si está disponible
                    if (vistaLista != null) {
                        List<String> nombresAtributos = new ArrayList<>();
                        for (DefinicionAtributo attr : definiciones) {
                            nombresAtributos.add(attr.getNombre());
                        }
                        vistaLista.actualizarTabla(listaEntidades);
                    }
                    
                    vista.mostrarMensaje("Entidad guardada exitosamente:\n" + entidad.toString());
                    
                    // Limpiar campos después de guardar
                    vista.limpiarCampos();
                } catch (Exception ex) {
                    vista.mostrarMensaje("Error al guardar la entidad: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        
        vista.getBtnListar().addActionListener(e -> {
            if (vistaLista != null) {
                vistaLista.setVisible(true);
                vistaLista.toFront();
            } else {
                mostrarVistaLista();
            }
        });
    }
    
    private void mostrarVistaLista() {
        try {
            // Cargar las entidades más recientes
            List<Map<String, Object>> entidades = dao.cargar();
            
            // Si no hay entidades cargadas, usar la lista local
            if (entidades == null || entidades.isEmpty()) {
                entidades = new ArrayList<>(listaEntidades);
                System.out.println("No se encontraron entidades en el archivo, usando lista local con " + 
                                   listaEntidades.size() + " entidades");
            } else {
                System.out.println("Se cargaron " + entidades.size() + " entidades del archivo");
            }
            
            // Obtener los nombres de atributos
            List<String> nombresAtributos = definiciones.stream()
                .map(DefinicionAtributo::getNombre)
                .toList();
            
            System.out.println("Mostrando lista con atributos: " + nombresAtributos);
            System.out.println("Número de entidades a mostrar: " + entidades.size());
            
            // Crear la vista de lista
            vistaLista = new VistaListaEntidades(nombresAtributos, entidades);
            
            // Crear el controlador (que configura los eventos de la vista)
            ControladorListaEntidades controlador = 
                new ControladorListaEntidades(nombresAtributos, entidades, dao);
            
            // Mostrar la vista en primer plano
            vistaLista.setVisible(true);
            vistaLista.toFront();
            vistaLista.requestFocus();
            
            System.out.println("Vista de lista mostrada correctamente");
        } catch (Exception ex) {
            System.err.println("Error al mostrar la vista de lista: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, 
                "Error al mostrar la lista de entidades: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Establece la vista de lista de entidades y configura sus eventos
     * @param vistaLista La vista de lista de entidades a vincular
     */
    public void setVistaLista(VistaListaEntidades vistaLista) {
        this.vistaLista = vistaLista;
    }
    
    /**
     * Obtiene la vista de entidad asociada a este controlador
     * @return La vista de entidad
     */
    public VistaEntidad getVista() {
        return vista;
    }
}
