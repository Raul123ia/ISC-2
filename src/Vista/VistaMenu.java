package Vista;
import java.util.List;
import java.util.ArrayList;
import Modelo.Usuario;
import Modelo.Proveedor;
import Modelo.CajaEstado;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.JDesktopPane;

public class VistaMenu {

	private JFrame frame;
	private JMenuBar BarraMenu;
	private JMenu Mventas,Mgestor ,Mgestion,Mconfiguracion,Mreportes,Msalida;
	private JMenuItem Moventas,MCancelav,Mdevolu,Moinventarios ;
	private JMenuItem Mocategorias ,Moproductos ,Moprovedores ;
	private JMenuItem MoTipoVenta ,Musuarios ,MoSalida,Mcaja;
	private JMenuItem menuReportes; // Elemento de menú para reportes
	private JDesktopPane Escritorio;
	private List<Usuario> listaUsuarios = new ArrayList<>();
	private List<Proveedor> listaProveedores = new ArrayList<>();
	private Modelo.CajaEstado estadoCaja;

	// Colores para la interfaz
	private final Color COLOR_FONDO = new Color(245, 137, 67);
	private final Color COLOR_PRIMARIO = new Color(60, 141, 188);
	private final Color COLOR_SECUNDARIO = new Color(0, 166, 90);
	private final Color COLOR_TEXTO = new Color(34, 45, 50);
	private final Color COLOR_HOVER = new Color(96, 92, 168);

	public VistaMenu() {
		initialize();
		// Cargar la lista de proveedores desde la base de datos
		listaProveedores = Proveedor.obtenerTodos();
			// Inicializar el estado de caja
			this.estadoCaja = new Modelo.CajaEstado();
	}

	private void initialize() {
		// Configurar el estilo visual global
		
		// Configuración del JFrame principal
		frame = new JFrame("Sistema de Gestión Comercial");
		frame.setBounds(100, 100, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(COLOR_FONDO);

		// Configuración de la barra de menú personalizada
		BarraMenu = new JMenuBar() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, COLOR_PRIMARIO, getWidth(), 0, new Color(41, 128, 185));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		BarraMenu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BarraMenu.setPreferredSize(new Dimension(frame.getWidth(), 40));
		frame.setJMenuBar(BarraMenu);

		// Estilo común para todos los menús
		Font menuFont = new Font("Segoe UI", Font.BOLD, 14);
		Color menuForeground = Color.WHITE;
		
		// Configuración de menús con estilo mejorado
		Mventas = createStyledMenu("Gestor de Ventas", menuFont, menuForeground);
		BarraMenu.add(Mventas);

		Mcaja = createStyledMenuItem("Caja", "Administrar caja", COLOR_SECUNDARIO);
		Mventas.add(Mcaja);

		Moventas = createStyledMenuItem("Ventas", "Registrar ventas", COLOR_SECUNDARIO);
		Mventas.add(Moventas);

		
		MCancelav = createStyledMenuItem("Cancelación de Venta", "Cancelar ventas", COLOR_SECUNDARIO);
		Mventas.add(MCancelav);

		
		Mgestion = createStyledMenu("Gestionar Productos", menuFont, menuForeground);
		BarraMenu.add(Mgestion);

		Moprovedores = createStyledMenuItem("Proveedores", "Gestionar proveedores", COLOR_SECUNDARIO);
		Mgestion.add(Moprovedores);

		Moproductos = createStyledMenuItem("Productos", "Gestionar productos", COLOR_SECUNDARIO);
		Mgestion.add(Moproductos);

		Moinventarios = createStyledMenuItem("Inventario", "Control de inventario", COLOR_SECUNDARIO);
		Mgestion.add(Moinventarios);

		Mconfiguracion = createStyledMenu("Configuración", menuFont, menuForeground);
		BarraMenu.add(Mconfiguracion);

		Musuarios = createStyledMenuItem("Usuarios", "Administrar usuarios", COLOR_SECUNDARIO);
		Mconfiguracion.add(Musuarios);

		Mocategorias = createStyledMenuItem("Categorías", "Administrar categorías", COLOR_SECUNDARIO);
		Mconfiguracion.add(Mocategorias);

		Mreportes = createStyledMenu("Reportes", menuFont, menuForeground);
		BarraMenu.add(Mreportes);

		// Inicializar y agregar los ítems al menú Mreportes
		menuReportes = createStyledMenuItem("Generar Reportes", "Crear informes", COLOR_SECUNDARIO);
		Mreportes.add(menuReportes);
		
		JMenuItem menuTickets = createStyledMenuItem("Ver Documentos", "Ver tickets guardados", COLOR_SECUNDARIO);
		menuTickets.addActionListener(e -> {
		    try {
		        // Abrir la carpeta de reportes para ver los tickets guardados
		        File carpetaReportes = new File("reportes");
		        if (!carpetaReportes.exists()) {
		            carpetaReportes.mkdir();
		            JOptionPane.showMessageDialog(frame, 
		                "No hay tickets guardados. La carpeta de reportes ha sido creada.", 
		                "Información", JOptionPane.INFORMATION_MESSAGE);
		        } else if (Desktop.isDesktopSupported()) {
		            Desktop.getDesktop().open(carpetaReportes);
		        } else {
		            JOptionPane.showMessageDialog(frame, 
		                "No se puede abrir la carpeta automáticamente. Ruta: " + carpetaReportes.getAbsolutePath(), 
		                "Información", JOptionPane.INFORMATION_MESSAGE);
		        }
		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(frame, 
		            "Error al abrir la carpeta de reportes: " + ex.getMessage(), 
		            "Error", JOptionPane.ERROR_MESSAGE);
		    }
		});
		Mreportes.add(menuTickets);
		
		Msalida = createStyledMenu("Salida", menuFont, menuForeground);
		BarraMenu.add(Msalida);

		MoSalida = createStyledMenuItem("Salir", "Cerrar aplicación", COLOR_SECUNDARIO);
		Msalida.add(MoSalida);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		// Configuración del escritorio con fondo degradado
		Escritorio = new JDesktopPane() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, COLOR_FONDO, getWidth(), getHeight(), new Color(245, 137, 67));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		frame.getContentPane().add(Escritorio, "name_153675376406100");
	}
	
	// Método para crear menús con estilo consistente
	private JMenu createStyledMenu(String title, Font font, Color foreground) {
		JMenu menu = new JMenu(title);
		menu.setFont(font);
		menu.setForeground(foreground);
		return menu;
	}
	
	// Método para crear elementos de menú con estilo consistente
	private JMenuItem createStyledMenuItem(String text, String tooltipText, Color hoverColor) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuItem.setForeground(COLOR_TEXTO);
		menuItem.setBackground(Color.WHITE);
		menuItem.setToolTipText(tooltipText);
		Border emptyBorder = new EmptyBorder(5, 15, 5, 15);
		menuItem.setBorder(emptyBorder);
		return menuItem;
	}
	
	

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public List<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public JMenuItem getMoinventarios() {
		return Moinventarios;
	}

	public JMenuItem getMoventas() {
		return Moventas;
	}

	public JMenuItem getMusuarios() {
		return Musuarios;
	}

	public JMenuItem getMoproductos() {
		return Moproductos;
	}

	public JMenuItem getMcaja() {
		return Mcaja;
	}

	public JMenuItem getMenuReportes() {
	    return menuReportes;
	}

	public void setMcaja(JMenuItem mcaja) {
		Mcaja = mcaja;
	}

	public JFrame getFrame()
	{ return this.frame;
	}
	
	/**
	 * Obtiene referencia al estado de caja actual
	 */
	public Modelo.CajaEstado getEstadoCaja() {
	    return this.estadoCaja;
	}

	/**
	 * Establece el estado de caja
	 * @param estadoCaja El nuevo estado de caja
	 */
	public void setEstadoCaja(Modelo.CajaEstado estadoCaja) {
	    this.estadoCaja = estadoCaja;
	}
	
	public JMenuItem getSalida()
	{
		return this.MoSalida;
	}
	public void setTitulo(String titulo)
	{
		this.frame.setTitle(titulo);
	}

	public  JDesktopPane getEscritorio()
	{
		return this.Escritorio;
	}
	
	

	public JMenu getMgestion() {
		return Mgestion;
	}

	public void setMgestion(JMenu mgestion) {
		Mgestion = mgestion;
	}

	public JMenuItem getMCancelav() {
		return MCancelav;
	}

	public void setMCancelav(JMenuItem mCancelav) {
		MCancelav = mCancelav;
	}

	public JMenuItem getMdevolu() {
		return Mdevolu;
	}

	public void setMdevolu(JMenuItem mdevolu) {
		Mdevolu = mdevolu;
	}

	public JMenuItem getMoprovedores() {
		return this.Moprovedores;
	}

	public JMenuItem getMocategorias() {
		return this.Mocategorias;
	}

	public JMenuItem getMoTipoVenta() {
		return MoTipoVenta;
	}

	public JMenuBar getBarraMenu() {
		return BarraMenu;
	}

	public void Menus(Boolean accion)
	{
		this.Mventas.setEnabled(accion);
		this.Mgestion.setEnabled(accion);
		this.Mconfiguracion.setEnabled(accion);
		this.Mreportes.setEnabled(accion);
		this.Msalida.setEnabled(accion);

	}
		
		/**
		 * Desbloquea completamente la interfaz del menú principal
		 * Asegura que todos los componentes de navegación estén habilitados
		 */
		public void desbloquearInterfaz() {
			// Habilitar el frame principal
			this.frame.setEnabled(true);
			
			// Habilitar la barra de menú
			this.BarraMenu.setEnabled(true);
			
			// Habilitar todos los menús
			Menus(true);
			
			// Habilitar cada elemento de menú individualmente para mayor seguridad
			if (this.Moventas != null) this.Moventas.setEnabled(true);
			if (this.MCancelav != null) this.MCancelav.setEnabled(true);
			if (this.Mdevolu != null) this.Mdevolu.setEnabled(true);
			if (this.Moinventarios != null) this.Moinventarios.setEnabled(true);
			if (this.Mocategorias != null) this.Mocategorias.setEnabled(true);
			if (this.Moproductos != null) this.Moproductos.setEnabled(true);
			if (this.Moprovedores != null) this.Moprovedores.setEnabled(true);
			if (this.MoTipoVenta != null) this.MoTipoVenta.setEnabled(true);
			if (this.Musuarios != null) this.Musuarios.setEnabled(true);
			if (this.MoSalida != null) this.MoSalida.setEnabled(true);
			if (this.Mcaja != null) this.Mcaja.setEnabled(true);
			if (this.menuReportes != null) this.menuReportes.setEnabled(true);
			
			// Refrescar la interfaz
			this.frame.repaint();
			this.BarraMenu.repaint();
		}
	
	/**
	 * Configura una ventana interna para que no se cierre completamente al hacer clic en la X
	 * @param ventana Ventana interna a configurar
	 */
	
	/**
	 * Muestra una ventana interna en el escritorio
	 * @param ventana Ventana interna a mostrar
	 */
		public void mostrarVentana(JInternalFrame ventana) {
		    if (!ventana.isVisible()) {
		        if (ventana.getParent() == null) {
		            this.Escritorio.add(ventana);
		        }
		        ventana.setVisible(true);
		    }
		    try {
		        ventana.setIcon(false);    // Por si está minimizada
		        ventana.setSelected(true); // Traerla al frente
		        ventana.toFront();         // Otra forma de asegurar visibilidad
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
}

