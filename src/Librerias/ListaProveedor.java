
package Librerias;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Modelo.Proveedor;

public class ListaProveedor extends Lista<Proveedor> {

    public DefaultTableModel getTablaModelo(ArrayList<String[]> lista) {
        String[] columnasnombre = {"Id Proveedor", "    Proveedor   "};
        DefaultTableModel tabla = new DefaultTableModel(columnasnombre, 0);
        
        if (lista != null && lista.size() > 0) {
            for (String[] nodo : lista) {
                this.insertar(new Proveedor(nodo[0], nodo[1]));
            }
        }
        
        if (getLista() != null && getLista().size() > 0) {
            for (Proveedor objeto : getLista()) {
                tabla.addRow(new Object[]{objeto.getIdProveedor(), objeto.getProveedor()});
            }
        }
        
        return tabla;
    }
}
