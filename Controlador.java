package es.studium.ConsultasPDF;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Controlador implements WindowListener, ActionListener
{
	Vista vista;
	Modelo modelo;
	
	Connection conexion = null;

	public Controlador(Vista v, Modelo m) 
	{
		this.vista = v;
		this.modelo = m;

		this.vista.ventana.addWindowListener(this);
		this.vista.btnPDF.addActionListener(this);

		// Conectar BD
		conexion = this.modelo.conectar();
		// Realizar consulta, y sacar informaci�n
		String informacion = "";
		informacion = this.modelo.consulta(conexion);
		// Rellenaremos TextArea
		this.vista.txaConsulta.append(informacion);
		// Cerrar la conexi�n
		this.modelo.cerrar(conexion);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// Pulsaste el bot�n
		// Opci�n 1
		// Sacar la informaci�n del TextArea e imprimir
		// Se crea el documento
		/*Document documento = new Document();
		File path = new File("Facturas.pdf");
		try
		{
			// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
			FileOutputStream ficheroPdf = new FileOutputStream("Facturas.pdf");
			// Se asocia el documento al OutputStream y se indica que el espaciado entre
			// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
			PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
			// Se abre el documento.
			documento.open();
			documento.add(new Paragraph("Listado de Facturas"));
			documento.add(new Paragraph(vista.txaConsulta.getText()));
			documento.close();
			// Y ahora abrimos el fichero para mostrarlo
			Desktop.getDesktop().open(path);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}*/
		// Opci�n 2
		// Conectar BD
		conexion = this.modelo.conectar();
		// Realizar consulta, y sacar informaci�n
		ArrayList<String> datos = this.modelo.consultarPDF(conexion);
		// Crear el PDF
		Document documento = new Document();
		try
		{
			// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
			FileOutputStream ficheroPdf = new FileOutputStream("Consulta.pdf");
			PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
			documento.open();
			documento.add(new Paragraph("Listado de Facturas",
					FontFactory.getFont("arial", // fuente 
							22, // tama�o 
							Font.ITALIC, // estilo 
							BaseColor.CYAN)));
			documento.add(new Paragraph());
			PdfPTable tabla = new PdfPTable(4);
			tabla.addCell("N� Factura");
			tabla.addCell("Fecha");
			tabla.addCell("DNI");
			tabla.addCell("Nombre");
			for(int i = 0; i < datos.size(); i++)
			{
				tabla.addCell(datos.get(i));
			}
			documento.add(tabla);
			documento.close();
			//Abrimos el archivo PDF reci�n creado
			try
			{
				File path = new File ("Consulta.pdf");
				Desktop.getDesktop().open(path);
			}
			catch(IOException ex)
			{
				System.out.println("Se ha producido un error al abrir el archivo PDF");
			}
		}
		catch (Exception e)
		{
			System.out.println("Se ha producido un error al abrir el archivo PDF");
		}
		// Cerrar la conexi�n
		this.modelo.cerrar(conexion);
	}

	@Override
	public void windowActivated(WindowEvent arg0){}

	@Override
	public void windowClosed(WindowEvent arg0){}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0){}

	@Override
	public void windowDeiconified(WindowEvent arg0){}

	@Override
	public void windowIconified(WindowEvent arg0){}

	@Override
	public void windowOpened(WindowEvent arg0){}
}