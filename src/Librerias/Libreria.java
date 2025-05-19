
package Librerias;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Libreria {

	public static String textoJustidicado(String cadena)
	{
		StringBuilder textoHTML =new StringBuilder("<html><body style='text-align:justify;'>");
		textoHTML.append(cadena);
		textoHTML.append("</body></html>");
		return textoHTML.toString(); 
	}
	
	public static boolean existeArchivo(String rutaArchivo) {
        // Método para verificar si el archivo existe
        File archivo = new File(rutaArchivo);
        return archivo.exists() ; // Devuelve true si existe 
    }

	
	public static ImageIcon obtenerLabelConImagen(String rutaImagen,int maxh,int maxv) {
        // Carga la imagen y la asigna a un JLabel
		ImageIcon imagen;
		java.awt.Image imagen2;
		String directorio="Imagenes\\";
		rutaImagen=directorio+rutaImagen+".png";
		
		System.out.println(maxh+"  "+maxv);
		if (existeArchivo(rutaImagen))
		 imagen = new ImageIcon(rutaImagen);
		else {
			imagen= new ImageIcon(directorio+"NoDisponible.png");
		}
		imagen2 = imagen.getImage();
		int originalh = imagen2.getWidth(null);
		int originalv = imagen2.getHeight(null);
		int nuevoh=originalh;
		int nuevov=originalv;
		//reducimios ancho
		
		if(nuevoh>maxh)
		{
			nuevoh=maxh;
			nuevov=(nuevoh*originalv)/originalh;
		}
		//reducimios altura
		
		if(nuevov>maxv)
		{
			nuevov=maxv;
			nuevoh=(nuevov*originalh)/originalv;
		}	
		
		
		System.out.println(nuevoh+" , "+nuevov);
		BufferedImage imagenresize = new BufferedImage (nuevoh,nuevov,BufferedImage.TYPE_INT_ARGB);
		Graphics2D grafico= imagenresize.createGraphics();
		grafico.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		grafico.drawImage(imagen2, 0, 0, nuevoh, nuevov, null);
        return new ImageIcon(imagenresize);
    }

	
}
