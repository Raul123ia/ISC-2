package Vista;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vista para mostrar un listado de entidades en forma de tabla
 */
public class VistaListaEntidades extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private List<String> nombresAtributos;
    private List<Map<String, Object>> entidades;

    /**
     * Constructor para la vista de lista de entidades
     * @param nombresAtributos Nombres de los atributos (columnas)
     * @param entidades Lista de entidades a mostrar
     */
    public VistaListaEntidades(List<String> nombresAtributos, List<Map<String, Object>> entidades) {
        this.nombresAtributos = nombresAtributos;
        this.entidades = entidades;
        
        setTitle("Lista de Entidades");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        inicializarComponentes();
        actualizarTabla(entidades);
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Crear modelo de tabla
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa
            }
        };
        
        // Agregar columnas
        for (String nombre : nombresAtributos) {
            modelo.addColumn(nombre);
        }
        
        // Crear tabla y panel de desplazamiento
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Crear botones
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver");
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        
        // Agregar componentes al layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    /**
     * Actualiza la tabla con nuevos datos
     * @param entidades Lista de entidades a mostrar
     */
    public void actualizarTabla(List<Map<String, Object>> entidades) {
        // Limpiar modelo
        modelo.setRowCount(0);
        
        // Agregar filas
        for (Map<String, Object> entidad : entidades) {
            Object[] fila = new Object[nombresAtributos.size()];
            
            for (int i = 0; i < nombresAtributos.size(); i++) {
                String nombreAtributo = nombresAtributos.get(i);
                fila[i] = entidad.get(nombreAtributo);
            }
            
            modelo.addRow(fila);
        }
        
        // Actualizar la referencia local
        this.entidades = entidades;
    }
    
    /**
     * Abre un diálogo para editar una fila
     * @param fila Índice de la fila a editar
     * @return Mapa con los nuevos valores o null si se cancela
     */
    public Map<String, Object> editarFila(int fila) {
        try {
            Map<String, Object> valores = new HashMap<>();
            Map<String, Object> original = entidades.get(fila);
            
            // Crear panel con campos
            JPanel panel = new JPanel(new GridLayout(nombresAtributos.size(), 2));
            Map<String, JTextField> campos = new HashMap<>();
            
            for (String nombre : nombresAtributos) {
                panel.add(new JLabel(nombre + ":"));
                JTextField campo = new JTextField(original.get(nombre) != null ? original.get(nombre).toString() : "");
                panel.add(campo);
                campos.put(nombre, campo);
            }
            
            // Mostrar diálogo
            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Entidad", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (resultado == JOptionPane.OK_OPTION) {
                // Recoger valores
                for (String nombre : nombresAtributos) {
                    valores.put(nombre, campos.get(nombre).getText());
                }
                return valores;
            }
            
            return null;
        } catch (Exception e) {
            mostrarMensaje("Error al editar fila: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Muestra un mensaje al usuario
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Crea un nuevo diálogo para agregar una fila
     * @return Mapa con los nuevos valores o null si se cancela
     */
    public Map<String, Object> nuevaFila() {
        try {
            Map<String, Object> valores = new HashMap<>();
            
            // Crear panel con campos
            JPanel panel = new JPanel(new GridLayout(nombresAtributos.size(), 2));
            Map<String, JTextField> campos = new HashMap<>();
            
            for (String nombre : nombresAtributos) {
                panel.add(new JLabel(nombre + ":"));
                JTextField campo = new JTextField();
                panel.add(campo);
                campos.put(nombre, campo);
            }
            
            // Mostrar diálogo
            int resultado = JOptionPane.showConfirmDialog(this, panel, "Nueva Entidad", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (resultado == JOptionPane.OK_OPTION) {
                // Recoger valores
                for (String nombre : nombresAtributos) {
                    valores.put(nombre, campos.get(nombre).getText());
                }
                return valores;
            }
            
            return null;
        } catch (Exception e) {
            mostrarMensaje("Error al crear nueva fila: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene el botón de eliminar
     * @return Botón eliminar
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    /**
     * Obtiene el botón de modificar
     * @return Botón modificar
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }
    
    /**
     * Obtiene el botón de agregar
     * @return Botón agregar
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
    
    /**
     * Obtiene el botón de volver
     * @return Botón volver
     */
    public JButton getBtnVolver() {
        return btnVolver;
    }
    
    /**
     * Obtiene la tabla de entidades
     * @return Tabla de entidades
     */
    public JTable getTabla() {
        return tabla;
    }
    
    /**
     * Método para confirmar acciones
     * @param mensaje Mensaje a mostrar
     * @return true si se confirma, false en caso contrario
     */
    public boolean confirmarAccion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", 
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
}