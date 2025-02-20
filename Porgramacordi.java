package Cordiprogramas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Porgramacordi extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Porgramacordi frame = new Porgramacordi();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Porgramacordi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridheight = 4;
        gbc_panel.gridwidth = 13;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        contentPane.add(panel, gbc_panel);
        
                // Botón "Agregar"
                JButton btnAgregar = new JButton("Agregar");
                btnAgregar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new VentanaAgregar().setVisible(true); // Abre una nueva ventana
                    }
                });
                GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
                gbc_btnAgregar.gridx = 0;
                gbc_btnAgregar.gridy = 8;
                gbc_btnAgregar.insets = new Insets(0, 0, 0, 5);
                contentPane.add(btnAgregar, gbc_btnAgregar);
                
                        // Botón "Eliminar"
                        JButton btnEliminar = new JButton("Eliminar");
                        btnEliminar.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                new VentanaEliminar().setVisible(true);
                            }
                        });
                        GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
                        gbc_btnEliminar.gridx = 2;
                        gbc_btnEliminar.gridy = 8;
                        gbc_btnEliminar.insets = new Insets(0, 0, 0, 5);
                        contentPane.add(btnEliminar, gbc_btnEliminar);
                                
                                        // Botón "Salir"
                                        JButton btnSalir = new JButton("Salir");
                                        btnSalir.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                System.exit(0);
                                            }
                                        });
                                        
                                                // Botón "Buscar"
                                                JButton btnBuscar = new JButton("Buscar");
                                                btnBuscar.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        new VentanaBuscar().setVisible(true);
                                                    }
                                                });
                                                GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
                                                gbc_btnBuscar.gridx = 5;
                                                gbc_btnBuscar.gridy = 8;
                                                gbc_btnBuscar.insets = new Insets(0, 0, 0, 5);
                                                contentPane.add(btnBuscar, gbc_btnBuscar);
                                        GridBagConstraints gbc_btnSalir = new GridBagConstraints();
                                        gbc_btnSalir.gridx = 7;
                                        gbc_btnSalir.gridy = 8;
                                        gbc_btnSalir.insets = new Insets(0, 0, 0, 5);
                                        contentPane.add(btnSalir, gbc_btnSalir);
    }
}

// Nueva ventana para "Agregar"
class VentanaAgregar extends JFrame {
    public VentanaAgregar() {
        setTitle("Agregar Producto");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.add(new JButton("Guardar"));
        add(panel);
    }
}

// Nueva ventana para "Eliminar"
class VentanaEliminar extends JFrame {
    public VentanaEliminar() {
        setTitle("Eliminar Producto");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.add(new JButton("Confirmar eliminación"));
        add(panel);
    }
}

// Nueva ventana para "Buscar"
class VentanaBuscar extends JFrame {
    public VentanaBuscar() {
        setTitle("Buscar Producto");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.add(new JButton("Buscar"));
        add(panel);
    }
}