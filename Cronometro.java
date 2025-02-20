package Cordiprogramas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Cronometro extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel Panelprincipal;
	private JTextField tiniciar;
	private JTextField tdetener;
	private JTextField ttranscurrido;
	private JButton Biniciar;
	private JButton Bdetener;
	private JButton Bsalida;
	private long tiempoinicio,tiempodetener;
	private double tiempotranscurrido;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cronometro frame = new Cronometro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cronometro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		Panelprincipal = new JPanel();
		Panelprincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(Panelprincipal);
		GridBagLayout gbl_panelprincipal = new GridBagLayout();
		gbl_panelprincipal.columnWidths = new int[] {118, 118, 118, 0};
		gbl_panelprincipal.rowHeights = new int[] {37, 37, 37, 0, 0, 0, 0};
		gbl_panelprincipal.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelprincipal.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		Panelprincipal.setLayout(gbl_panelprincipal);
		
		 Biniciar = new JButton("Iniciar");
		Biniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_Biniciar = new GridBagConstraints();
		gbc_Biniciar.insets = new Insets(0, 0, 5, 5);
		gbc_Biniciar.gridx = 0;
		gbc_Biniciar.gridy = 0;
		Panelprincipal.add(Biniciar, gbc_Biniciar);
		
		Biniciar.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("Iniciar");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		Panelprincipal.add(lblNewLabel, gbc_lblNewLabel);
		
		tiniciar = new JTextField();
		GridBagConstraints gbc_tiniciar = new GridBagConstraints();
		gbc_tiniciar.insets = new Insets(0, 0, 5, 0);
		gbc_tiniciar.fill = GridBagConstraints.HORIZONTAL;
		gbc_tiniciar.gridx = 2;
		gbc_tiniciar.gridy = 0;
		Panelprincipal.add(tiniciar, gbc_tiniciar);
		tiniciar.setColumns(10);
		
		 Bdetener = new JButton("Detener");
		GridBagConstraints gbc_Bdetener = new GridBagConstraints();
		gbc_Bdetener.insets = new Insets(0, 0, 5, 5);
		gbc_Bdetener.gridx = 0;
		gbc_Bdetener.gridy = 1;
		Panelprincipal.add(Bdetener, gbc_Bdetener);
		this.Bdetener.addActionListener(this);
		
		JLabel lblNewLabel_1 = new JLabel("Detenerse");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		Panelprincipal.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		tdetener = new JTextField();
		GridBagConstraints gbc_tdetener = new GridBagConstraints();
		gbc_tdetener.insets = new Insets(0, 0, 5, 0);
		gbc_tdetener.fill = GridBagConstraints.HORIZONTAL;
		gbc_tdetener.gridx = 2;
		gbc_tdetener.gridy = 1;
		Panelprincipal.add(tdetener, gbc_tdetener);
		tdetener.setColumns(10);
		
		 Bsalida = new JButton("Salida");
		GridBagConstraints gbc_Bsalida = new GridBagConstraints();
		gbc_Bsalida.insets = new Insets(0, 0, 5, 5);
		gbc_Bsalida.gridx = 0;
		gbc_Bsalida.gridy = 2;
		Panelprincipal.add(Bsalida, gbc_Bsalida);
		
		JLabel lblNewLabel_2 = new JLabel("Tiempo Transcurrido");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		Panelprincipal.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		ttranscurrido = new JTextField();
		GridBagConstraints gbc_ttranscurrido = new GridBagConstraints();
		gbc_ttranscurrido.insets = new Insets(0, 0, 5, 0);
		gbc_ttranscurrido.fill = GridBagConstraints.HORIZONTAL;
		gbc_ttranscurrido.gridx = 2;
		gbc_ttranscurrido.gridy = 2;
		Panelprincipal.add(ttranscurrido, gbc_ttranscurrido);
		ttranscurrido.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.VERTICAL;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		Panelprincipal.add(scrollPane, gbc_scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	
	if(e.getSource()==Biniciar) 
	
	{   this.tiempoinicio = System.currentTimeMillis();
	    this.tiniciar.setText(String.valueOf(this.tiempoinicio));
	    this.tdetener.setText("");
	    this.ttranscurrido.setText("");
        }
	
		else if (e.getSource() == Bdetener)

		{
			tiempodetener = System.currentTimeMillis();
			this.tdetener.setText(String.valueOf(this.tiempodetener));
			tiempotranscurrido = (tiempodetener - tiempoinicio) / 1000;
			this.ttranscurrido.setText(String.valueOf((this.tiempotranscurrido)));
		}

		else if (e.getSource() == Bsalida)

		{  this.dispose();
		                         }
	
	
	}

}
