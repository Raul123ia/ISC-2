package Utilidades;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para generar reportes en formato PDF
 */
public class GeneradorPDF {
    
    /**
     * Genera un reporte PDF genérico
     * @param rutaArchivo ruta donde se guardará el archivo PDF
     * @param titulo título principal del reporte
     * @param subtitulo subtítulo o descripción del reporte
     * @param columnas nombres de las columnas de la tabla
     * @param datos matriz con los datos para la tabla
     * @return true si el reporte se generó correctamente, false en caso contrario
     */
    public boolean generarReportePDF(String rutaArchivo, String titulo, String subtitulo, 
            String[] columnas, Object[][] datos) {
        
        Document documento = new Document(PageSize.A4);
        
        try {
            // Asegurar que exista el directorio de reportes
            File dirReportes = new File("reportes");
            if (!dirReportes.exists()) {
                dirReportes.mkdirs();
            }
            
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();
            
            // Titulo principal
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph parrafoTitulo = new Paragraph(titulo, fuenteTitulo);
            parrafoTitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafoTitulo);
            documento.add(new Paragraph(" ")); // Espacio
            
            // Subtitulo
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 14);
            Paragraph parrafoSubtitulo = new Paragraph(subtitulo, fuenteSubtitulo);
            parrafoSubtitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafoSubtitulo);
            documento.add(new Paragraph(" ")); // Espacio
            
            // Fecha de generación
            Font fuenteFecha = FontFactory.getFont(FontFactory.HELVETICA_ITALIC, 10);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Paragraph parrafoFecha = new Paragraph("Generado: " + formatoFecha.format(new Date()), fuenteFecha);
            parrafoFecha.setAlignment(Element.ALIGN_RIGHT);
            documento.add(parrafoFecha);
            documento.add(new Paragraph(" ")); // Espacio
            
            // Crear la tabla
            PdfPTable tabla = new PdfPTable(columnas.length);
            tabla.setWidthPercentage(100);
            
            // Agregar encabezados
            Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            for (String columna : columnas) {
                PdfPCell celda = new PdfPCell(new Phrase(columna, fuenteEncabezado));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(5);
                tabla.addCell(celda);
            }
            
            // Agregar datos
            Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA, 10);
            for (Object[] fila : datos) {
                for (Object celda : fila) {
                    PdfPCell celdaPdf = new PdfPCell(new Phrase(celda != null ? celda.toString() : "", fuenteDatos));
                    celdaPdf.setPadding(4);
                    tabla.addCell(celdaPdf);
                }
            }
            
            documento.add(tabla);
            
            documento.close();
            return true;
            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            try {
                if (documento.isOpen()) {
                    documento.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}
