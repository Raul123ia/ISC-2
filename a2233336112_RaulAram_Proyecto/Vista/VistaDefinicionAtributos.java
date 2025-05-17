
package Vista;

import Modelo.DefinicionAtributo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para definir los atributos de la entidad
 */
public class VistaDefinicionAtributos extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelAtributos;
    private JTextField txtNombreAtributo;
    private JComboBox<String> cmbTipoAtributo;
    private JButton btnAgregarAtributo;
    private JButton btnTerminar;
    private JList<DefinicionAtributo> listaAtributos;
    private DefaultListModel<DefinicionAtributo> modeloLista;
    private JButton btnEliminarAtributo;
    
    private List<DefinicionAtributo> definiciones;
    
    /**
     * Constructor que inicializa la vista
     */
    public VistaDefinicionAtributos() {
        definiciones = new ArrayList<>();
        
        // Configuración de la ventana
        setTitle("Definición de Atributos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Inicialización de componentes
        inicializarComponentes();
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JLabel lblTitulo = new JLabel("Definición de Atributos para la Entidad");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel para agregar atributos
        panelAtributos = new JPanel(new GridBagLayout());
        panelAtributos.setBorder(BorderFactory.createTitledBorder("Nuevo Atributo"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nombre del atributo
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelAtributos.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtNombreAtributo = new JTextField(20);
        panelAtributos.add(txtNombreAtributo, gbc);
        
        // Tipo de atributo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panelAtributos.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        cmbTipoAtributo = new JComboBox<>(new String[] {"String", "Integer", "Double", "Boolean", "Date"});
        panelAtributos.add(cmbTipoAtributo, gbc);
        
        // Botón para agregar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        btnAgregarAtributo = new JButton("Agregar Atributo");
        panelAtributos.add(btnAgregarAtributo, gbc);
        
        // Panel para lista de atributos
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.setBorder(BorderFactory.createTitledBorder("Atributos Definidos"));
        
        modeloLista = new DefaultListModel<>();
        listaAtributos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaAtributos);
        panelLista.add(scrollLista, BorderLayout.CENTER);
        
        // Botón para eliminar atributo
        btnEliminarAtributo = new JButton("Eliminar Atributo");
        panelLista.add(btnEliminarAtributo, BorderLayout.SOUTH);
        
        // Panel central con ambos paneles
        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCentro.add(panelAtributos);
        panelCentro.add(panelLista);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        
        // Botón para terminar
        btnTerminar = new JButton("Terminar y Continuar");
        btnTerminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnTerminar.setPreferredSize(new Dimension(200, 40));
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnTerminar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
    }
    
    /**
     * Agrega un atributo a la lista
     */
    public void agregarAtributo() {
        String nombre = txtNombreAtributo.getText().trim();
        String tipo = (String) cmbTipoAtributo.getSelectedItem();
        
        if (!nombre.isEmpty() && tipo != null) {
            DefinicionAtributo atributo = new DefinicionAtributo(nombre, tipo);
            definiciones.add(atributo);
            modeloLista.addElement(atributo);
            txtNombreAtributo.setText("");
            txtNombreAtributo.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese un nombre para el atributo", 
                "Dato requerido", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Elimina un atributo de la lista
     */
    public void eliminarAtributo() {
        int indice = listaAtributos.getSelectedIndex();
        if (indice != -1) {
            definiciones.remove(indice);
            modeloLista.remove(indice);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un atributo para eliminar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Establece el listener para el botón de agregar atributo
     */
    public void setAgregarAtributoListener(ActionListener listener) {
        btnAgregarAtributo.addActionListener(listener);
    }
    
    /**
     * Establece el listener para el botón de eliminar atributo
     */
    public void setEliminarAtributoListener(ActionListener listener) {
        btnEliminarAtributo.addActionListener(listener);
    }
    
    /**
     * Establece el listener para el botón de terminar
     */
    public void setTerminarListener(ActionListener listener) {
        btnTerminar.addActionListener(listener);
    }
    
    /**
     * Obtiene la lista de definiciones de atributos
     */
    public List<DefinicionAtributo> getDefiniciones() {
        return new ArrayList<>(definiciones);
    }
    
    /**
     * Muestra un mensaje al usuario
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
