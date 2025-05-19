package Librerias;

import javax.swing.table.DefaultTableModel;

import Modelo.TipoVenta;

public class ListaTipoVenta extends Lista<TipoVenta> {

 
	public DefaultTableModel getTablaModelo()
	{   String[] columnasnombre={"Id Tipo Venta","Etiqueta Tipo Venta "};
		DefaultTableModel tabla = new DefaultTableModel(columnasnombre,0) ;
		if(getLista().size()>1)
		for(TipoVenta objeto:getLista()) 
			tabla.addRow(new Object[]{objeto.getId(),objeto.getEtiqueta()});
		return tabla;
	}
		
}
