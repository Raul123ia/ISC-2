package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Vista para capturar datos de una entidad
 */
public class VistaEntidad extends JFrame {
    private JPanel panelCampos;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnListar;
    
    private Map<String, JComponent> camposDinamicos;
    
    /**
     * Constructor que inicializa la vista
     */
    public VistaEntidad() {
        setTitle("Captura de Datos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        camposDinamicos = new HashMap<>();
        inicializarComponentes();
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Captura de Datos de la Entidad");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel con scroll para los campos
        panelCampos = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(panelCampos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Datos"));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        btnListar = new JButton("Ver Listado");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnListar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
    }
    
    /**
     * Agrega un campo dinámico según el tipo especificado
     * @param nombre Nombre del atributo
     * @param tipo Tipo de dato
     */
    public void agregarCampo(String nombre, String tipo) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = camposDinamicos.size();
        gbc.weightx = 0.3;
        
        // Etiqueta
        JLabel etiqueta = new JLabel(nombre + ":");
        panelCampos.add(etiqueta, gbc);
        
        // Campo de entrada según el tipo
        JComponent campo;
        switch (tipo) {
            case "Boolean":
                campo = new JComboBox<>(new String[] {"true", "false"});
                break;
            case "Integer":
            case "Double":
            case "String":
            case "Date":
            default:
                campo = new JTextField(15);
                break;
        }
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panelCampos.add(campo, gbc);
        
        camposDinamicos.put(nombre, campo);
        
        // Actualizar la interfaz
        panelCampos.revalidate();
        panelCampos.repaint();
    }
    
    /**
     * Obtiene los valores ingresados en los campos
     * @return Mapa con los valores
     */
    public Map<String, Object> obtenerValores() {
        Map<String, Object> valores = new HashMap<>();
        
        if (camposDinamicos == null) {
            System.out.println("ADVERTENCIA: No hay campos dinámicos definidos");
            return valores;
        }
        
        for (Map.Entry<String, JComponent> entry : camposDinamicos.entrySet()) {
            String clave = entry.getKey();
            JComponent campo = entry.getValue();
            String valor = "";
            
            if (campo instanceof JTextField) {
                valor = ((JTextField) campo).getText();
            } else if (campo instanceof JComboBox) {
                Object seleccionado = ((JComboBox<?>) campo).getSelectedItem();
                valor = seleccionado != null ? seleccionado.toString() : "";
            }
            
            // Solo agregar el valor si no está vacío
            if (valor != null && !valor.trim().isEmpty()) {
                valores.put(clave, valor);
            }
        }
        return valores;
    }
    
    /**
     * Obtiene el botón de guardar
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }
    
    /**
     * Obtiene el botón de cancelar
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }
    
    /**
     * Obtiene el botón de listar
     */
    public JButton getBtnListar() {
        return btnListar;
    }
    
    /**
     * Muestra un mensaje al usuario
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    /**
     * Limpia todos los campos de texto después de guardar una entidad
     */
    public void limpiarCampos() {
        for (JComponent campo : camposDinamicos.values()) {
            if (campo instanceof JTextField) {
                ((JTextField) campo).setText("");
            } else if (campo instanceof JComboBox) {
                ((JComboBox<?>) campo).setSelectedIndex(0);
            }
        }
    }
}
